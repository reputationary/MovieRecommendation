package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.MovieCount;
import com.example.pojo.THotMovies;

import java.util.List;

public interface THotMoviesService extends IService<THotMovies> {
    //获取热度前十的电影
    public List<MovieCount> getHotMovies();
}
