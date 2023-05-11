package com.example.pojo;

import lombok.Data;

@Data
public class MovieRatings {
    private int userId;
    private int movieId;
    private int score;
}
