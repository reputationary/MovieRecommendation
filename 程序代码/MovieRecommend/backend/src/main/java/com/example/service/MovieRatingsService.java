package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.MovieRatings;
import com.example.pojo.MyMovies;

import java.util.List;

public interface MovieRatingsService extends IService<MovieRatings> {
    public List<MyMovies> getMyMovies(int userId,int total,int page,int pageSize);
}
