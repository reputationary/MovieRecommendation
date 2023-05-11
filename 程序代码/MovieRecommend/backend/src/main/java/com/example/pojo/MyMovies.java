package com.example.pojo;

import lombok.Data;

@Data
public class MyMovies {
    private int id;
    private String title;
    private String imgUrl;
    private double avgscore;
    private int userId;
    private int score;
}
