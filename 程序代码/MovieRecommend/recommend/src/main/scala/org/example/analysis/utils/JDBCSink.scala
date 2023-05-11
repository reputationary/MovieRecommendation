package org.example.analysis.utils

import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}
import org.apache.spark.sql.{ForeachWriter, Row}

class JDBCSink(url:String,username:String ,password:String) extends ForeachWriter[Row] {
  var connection :Connection = _
  var statement : Statement = _
  var resultSet : ResultSet = _

  override def open(partitionId: Long, epochId: Long): Boolean = {
    Class.forName("com.mysql.cj.jdbc.Driver")
    connection = DriverManager.getConnection(url,username,password)
    statement = connection.createStatement()
    return true
  }

  override def process(value: Row): Unit = {
    val movieId = value.getAs[Int]("movie_id")
    val counts = value.getAs[Long]("counts")

    // 查询语句
    val querySql = "select movie_id from t_hot_movies where movie_id = '"+movieId+"'"

    // 修改语句
    val updateSql = "update t_hot_movies set counts = "+counts+" where movie_id = '"+movieId+"'"

    // 增加语句
    val insertSql = "insert into t_hot_movies (movie_id,counts) values('"+movieId+"',"+counts+")"

    // 查询movieId，如果表中没有movieId，就增加movieId和count，如果表中有movieId，就修改count
    resultSet = statement.executeQuery(querySql)
    if(resultSet.next()){
      statement.executeUpdate(updateSql)
    }else{
      try{
        statement.execute(insertSql)
      }
      catch {
        case ex:SQLException =>{
          println(ex)
        }
      }
    }

  }

  override def close(errorOrNull: Throwable): Unit = {
    statement.close()
    connection.close()
  }
}
