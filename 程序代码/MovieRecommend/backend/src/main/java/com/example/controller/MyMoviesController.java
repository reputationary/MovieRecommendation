package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.pojo.MovieRatings;
import com.example.pojo.MyMovies;
import com.example.pojo.PageBean;
import com.example.service.MovieRatingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/myMovies")
public class MyMoviesController {
    @Autowired
    private MovieRatingsService movieRatingsService;

    /**
     * 根据用户id获取我看过的电影
     */
    @GetMapping
    public Result<PageBean> getMy(int userId,int page,int pageSize){
        LambdaQueryWrapper<MovieRatings> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MovieRatings::getUserId,userId);
        int total = movieRatingsService.count(queryWrapper);
        List<MyMovies> myMovies = movieRatingsService.getMyMovies(userId,total,page,pageSize);
        PageBean pageBean=new PageBean();
        pageBean.setTotal(total);
        pageBean.setMyMovies(myMovies);
        return Result.success(pageBean);
    }

}
