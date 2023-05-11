package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.pojo.MovieRatings;
import com.example.pojo.MoviesScore;
import com.example.service.MovieRatingsService;
import com.example.service.MoviesScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private MoviesScoreService moviesScoreService;

    @Autowired
    private MovieRatingsService movieRatingsService;

    /**
     * 根据电影id查询电影详细信息
     */
    @GetMapping
    public Result<MoviesScore> get(int id){
        LambdaQueryWrapper<MoviesScore> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MoviesScore::getId,id);
        MoviesScore one = moviesScoreService.getOne(queryWrapper);
        return Result.success(one);
    }

    /**
     * 对电影进行评分
     */
    @PostMapping
    public Result<String> rate(@RequestBody MovieRatings movieRatings){
        LambdaQueryWrapper<MovieRatings> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MovieRatings::getUserId,movieRatings.getUserId());
        queryWrapper.eq(MovieRatings::getMovieId,movieRatings.getMovieId());
        MovieRatings one = movieRatingsService.getOne(queryWrapper);
        if(one==null){
            movieRatingsService.save(movieRatings);
        }else{
            movieRatingsService.update(movieRatings,queryWrapper);
        }
        return Result.success("评分成功");
    }
}
