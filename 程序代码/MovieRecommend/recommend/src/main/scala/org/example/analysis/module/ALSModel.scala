package org.example.analysis.module

import breeze.numerics.sqrt
import org.apache.spark.api.java.JavaRDD.fromRDD
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SaveMode, SparkSession}
import org.apache.spark.SparkContext
import org.example.analysis.bean.RecommendItem

import scala.collection.mutable.ArrayBuffer

object ALSModel {
  def main(args: Array[String]): Unit = {
    //1.准备环境
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("ALSModeling")
      .config("spark.local.dir", "temp")
      .config("spark.sql.shuffle.partitions", "4")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")
    import spark.implicits._

    //2.加载数据并处理
    val path = "recommend/data/input/rating.txt"
    val data= spark.sparkContext.textFile(path)
    val trainData = data.map(line => {
      val arr = line.split(" ")
      val userId: Int = arr(0).toInt
      val movieId: Int = arr(1).toInt
      val score: Double = arr(2).toDouble
      Rating(userId, movieId, score)
    })

    //3.划分数据集Array(80%训练集, 20%测试集)
    val randomSplits= trainData.randomSplit(Array(0.8, 0.2), 11L)

    //训练模型
    val model = ALS.train(randomSplits(0), 20, 15, 0.09)

    //计算均方根误差
    val realProduct = randomSplits(1).map(t => {
      (t.user, t.product)
    })
    val predictRating = model.predict(realProduct)
    val observed = randomSplits(1).map(t => {
      ((t.user, t.product), t.rating)
    })
    val predicted = predictRating.map(t => {
      ((t.user, t.product), t.rating)
    })
    val rmse = sqrt(
      observed.join(predicted).map {
        case ((userId, productId), (actual, pre)) =>
          val r = actual - pre
          r * r
      }.mean()
    )
    println("均方根误差为："+rmse)

    if(rmse<=1.5){
      val result1= model.recommendProductsForUsers(12)
      val recommendResultDF: DataFrame = result1.map(t => {
        val userId: Int = t._1
        val recommends: String = t._2.map(_.product).mkString(",")
        (userId, recommends)
      }).toDF("user_id", "recommends")
      recommendResultDF.show(false)

      //将推荐结果写入mysql
      val properties = new java.util.Properties()
      properties.setProperty("user", "root")
      properties.setProperty("password", "lumos1989")
      recommendResultDF.coalesce(1)
        .write
        .mode(SaveMode.Overwrite)
        .jdbc("jdbc:mysql://localhost:3306/movie_recommend?useUnicode=true&characterEncoding=utf8",
          "recommend",properties
        )

      println("推荐结果已写入mysql")
      //movieIds.show()

      //计算物品与物品之间的相似度，实现基于物品推荐物品
      val itemFactors = model.productFeatures
      val arr3 = ArrayBuffer[(Int,String)]()

      for (i<- 1 to 250){
        val iFactors = itemFactors.keyBy(x => x._1).lookup(i).head._2
        //计算向量之间的夹角余弦
        val cosI = itemFactors.map { case (id, factor) => {
          val cos = cosArray(iFactors, factor)
          (id, cos)
        }}
        val ITop20 = cosI.sortBy { case (id, cos) => -cos }.take(12)
        //ITop20.foreach(println)
        val movieId=i;
        val arr = ITop20.map(_._1).mkString(",")
        val re=(movieId,arr)
        arr3+=re
      }
      val recommendItem = sc.makeRDD(arr3).toDF("movie_id","recommends")
      recommendItem.show(false)
      recommendItem.coalesce(1)
        .write
        .mode(SaveMode.Overwrite)
        .jdbc("jdbc:mysql://localhost:3306/movie_recommend?useUnicode=true&characterEncoding=utf8",
          "recommend_item",properties
        )
      println("推荐结果已写入mysql")

    }
  }

  //计算向量之间的夹角余弦
  def cosArray(a1: Array[Double],a2: Array[Double])={
    val a1a2=a1 zip a2
    val a1a2Fenzi=a1a2.map(x=>x._1*x._2).sum
    val a1FenMu=Math.sqrt(a1.map(x=>x*x).sum)
    val a2FenMu=Math.sqrt(a2.map(x=>x*x).sum)
    val a1a2Cos =a1a2Fenzi/(a1FenMu*a2FenMu)
    a1a2Cos
  }
}
