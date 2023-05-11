package org.example.analysis.bean

case class Movie(
                id: Int,
                title: String,
                src: String,
                director: String,
                summary: String,
                img_url: String,
                year: Int,
                country: String,
                category: String
                )extends Serializable
