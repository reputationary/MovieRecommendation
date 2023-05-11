package org.example.analysis.batch

import org.apache.spark.SparkContext
import org.apache.spark.sql.{Dataset, SaveMode, SparkSession}
import org.example.analysis.bean.Movie

object DataLoad {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("StreamingAnalysis").master("local[*]")
      .config("spark.sql.shuffle.partitions", "4")//本次测试时将分区数设置小一点,实际开发中可以根据集群规模调整大小,默认200
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")
    import spark.implicits._
    import org.apache.spark.sql.functions._

    val properties = new java.util.Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "lumos1989")
    val movieInfo: Dataset[Movie] = spark.read.jdbc(
      "jdbc:mysql://localhost:3306/movie_recommend?useUnicode=true&characterEncoding=utf8",
      "movies",
      properties
    ).as[Movie]

    movieInfo.show()

    val newMovies = movieInfo.map(item => {
      val img_url="img/"+item.id+".jpg"
      var title=item.title
      if(title.length>8){
        title=item.title.substring(0,7)+".."
      }
      (item.id,title,item.src,item.director,item.summary,img_url,item.year,item.country,item.category)
    }).toDF("id","title","src","director","summary","img_url","year","country","category")

    newMovies.show()

    newMovies.write
      .mode(SaveMode.Overwrite)
      .jdbc("jdbc:mysql://localhost:3306/movie_recommend?characterEncoding=UTF-8","movies2",properties)
  }
}
