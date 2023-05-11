package org.example.analysis.streaming

import com.google.gson.Gson
import org.apache.spark.SparkContext
import org.apache.spark.sql.streaming.{ProcessingTime, Trigger}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SaveMode, SparkSession}
import org.example.analysis.bean.Rate
import org.example.analysis.utils.JDBCSink

//实时从Kafka的movie主题消费数据，并做实时统计分析，结果保存到mysql数据库
object StreamingAnalysis {
  def main(args: Array[String]): Unit = {
    //准备环境
    val spark: SparkSession = SparkSession.builder().appName("StreamingAnalysis").master("local[2]")
      .config("spark.sql.shuffle.partitions", "4")//本次测试时将分区数设置小一点,实际开发中可以根据集群规模调整大小,默认200
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")
    import org.apache.spark.sql.functions._
    import spark.implicits._

    //加载数据
    val kafkaDF: DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "movie")
      .load()
    val valueDS: Dataset[String] = kafkaDF.selectExpr("CAST(value AS STRING)").as[String]

    //处理数据
    //解析json,将每一条json字符串解析为一个样例类对象
    val ratingDS: Dataset[Rate] = valueDS.map(jsonStr => {
      val gson = new Gson()
      //json ---> 对象
      gson.fromJson(jsonStr, classOf[Rate])
    })

    //实时分析
    //实时分析需求1:统计top10热门电影，即评分人数最多的10部电影
    val result1: Dataset[Row] = ratingDS.groupBy('movie_id)
      .agg(
        count('movie_id) as "counts"
      )

    //输出结果
    val url = "jdbc:mysql://localhost:3306/movie_recommend?characterEncoding=UTF-8"
    val username = "root"
    val password = "lumos1989"

    val writer = new JDBCSink(url,username,password)
    val query = result1.writeStream
      .foreach(writer)
      .outputMode("update")
      //                .format("console")
      .trigger(ProcessingTime("5 seconds"))
      .start()
    query.awaitTermination()

    //关闭资源
    spark.stop()
  }
}
