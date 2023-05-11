package org.example.analysis.mock

import com.google.gson.Gson
import org.example.analysis.bean.Rate
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * 用户评分数据在线模拟程序
 */
object Simulator {
  //模拟数据
  //用户ID
  val arr1 = ArrayBuffer[Int]()
  for (i <- 1 to 200) {
    arr1 +=  i
  }
  //电影id
  val arr2 = ArrayBuffer[Int]()
  for (i <- 1 to 250) {
    arr2 +=  i
  }
  //评分
  val arr3 = Array(1, 2, 3,4,5)

  def genRating() = {
    val userIDRandom = arr1(Random.nextInt(arr1.length))
    val movieIDRandom = arr2(Random.nextInt(arr2.length))
    val scoreRandom = arr3(Random.nextInt(arr3.length))
    Rate(userIDRandom,movieIDRandom,scoreRandom)
  }

  def main(args: Array[String]): Unit = {
    val gson = new Gson()
    for (i <- 1 to 20) {
      println(s"第{$i}条")
      val jsonString = gson.toJson(genRating())
      println(jsonString)
      //Thread.sleep(200)
    }
  }
}
