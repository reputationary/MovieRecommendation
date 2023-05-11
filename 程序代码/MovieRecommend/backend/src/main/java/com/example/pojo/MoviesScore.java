package com.example.pojo;

import lombok.Data;

@Data
public class MoviesScore {
    private int id;
    private String title;
    private String src;
    private String director;
    private String summary;
    private String imgUrl;
    private int year;
    private String country;
    private String category;
    private int movieId;
    private double avgscore;
}
