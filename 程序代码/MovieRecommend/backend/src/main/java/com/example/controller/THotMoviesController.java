package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.pojo.MovieCount;
import com.example.pojo.MoviesScore;
import com.example.pojo.THotMovies;
import com.example.service.THotMoviesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hotMovies")
public class THotMoviesController {
    @Autowired
    private THotMoviesService tHotMoviesService;

    /**
     * 获取热度前十的电影，即评论人数最多的前十部电影
     */
    @GetMapping
    @Transactional
    public Result<List<MovieCount>> getHot(){
        List<MovieCount> hotMovies = tHotMoviesService.getHotMovies();
        return Result.success(hotMovies);
    }
}
