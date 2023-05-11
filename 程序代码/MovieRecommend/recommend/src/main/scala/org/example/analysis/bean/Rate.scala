package org.example.analysis.bean

case class Rate(
                 user_id: Int,
                 movie_id: Int,
                 score: Int
                 ) extends Serializable
