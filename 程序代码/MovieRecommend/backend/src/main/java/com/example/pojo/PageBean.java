package com.example.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageBean {
    private int total;
    private List<MyMovies> myMovies;
}
