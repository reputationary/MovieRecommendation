package com.example.pojo;

import lombok.Data;

@Data
public class MovieCount {
    private int id;
    private String title;

    private Long counts;
}
