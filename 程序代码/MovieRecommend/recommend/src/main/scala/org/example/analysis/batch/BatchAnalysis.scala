package org.example.analysis.batch

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Dataset, Row, SaveMode, SparkSession}
import org.example.analysis.bean.{Movie, MovieWithScore, Rate}

import java.util.Properties

/**
 * 离线分析电影评分情况
 */
object BatchAnalysis {
  def main(args: Array[String]): Unit = {
    //准备环境
    val spark: SparkSession = SparkSession.builder().appName("StreamingAnalysis").master("local[*]")
      .config("spark.sql.shuffle.partitions", "4")//本次测试时将分区数设置小一点,实际开发中可以根据集群规模调整大小,默认200
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")
    import spark.implicits._
    import org.apache.spark.sql.functions._

    //加载数据
    val ratingInfo: Dataset[String] = spark.read.textFile("recommend/data/input/rating.txt")
    val ratingDS: Dataset[Rate] = ratingInfo.map(line => {
      val arr: Array[String] = line.split(" ")
      Rate(arr(0).toInt, arr(1).toInt, arr(2).toInt)
    }).as[Rate]

    val scores= ratingDS.groupBy('movie_id)
      .agg(
        avg('score)*2 as "avgscore"
      )
      .orderBy('avgscore.desc)
      .as[MovieWithScore]


    val frame = scores.map(item => {
      val avgscore = item.avgscore.formatted("%.2f")
      (item.movie_id, avgscore)
    }).toDF("movie_id", "avgscore")


    frame.show()

    val properties = new java.util.Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "lumos1989")
    val movieInfo: Dataset[Movie] = spark.read.jdbc(
      "jdbc:mysql://localhost:3306/movie_recommend?useUnicode=true&characterEncoding=utf8",
      "movies2",
      properties
    ).as[Movie]

    val movieWithScore= movieInfo
      .join(frame.dropDuplicates("movie_id"), $"id"===$"movie_id")





    movieWithScore.write
      .mode(SaveMode.Overwrite)
      .jdbc("jdbc:mysql://localhost:3306/movie_recommend?characterEncoding=UTF-8","movies_score",properties)

    spark.stop()
  }
}
