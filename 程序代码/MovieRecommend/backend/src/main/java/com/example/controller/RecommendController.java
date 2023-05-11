package com.example.controller;

import com.example.common.Result;
import com.example.pojo.MoviesScore;
import com.example.pojo.THotMovies;
import com.example.service.RecommendItemService;
import com.example.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;

    @Autowired
    private RecommendItemService recommendItemService;

    /**
     * 根据用户id获取基于用户推荐的电影
     */
    @GetMapping("/forUser")
    public Result<List<MoviesScore>> get(int userId){
        List<MoviesScore> recommend = recommendService.getRecommend(userId);
        return Result.success(recommend);
    }

    /**
     * 根据电影id，推荐与此电影相似的电影
     */
    @GetMapping("/onMovie")
    public Result<List<MoviesScore>> getOnMovie(int movieId){
        List<MoviesScore> recommend = recommendItemService.getRecommend(movieId);
        return Result.success(recommend);
    }
}
