package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.MoviesScore;
import com.example.pojo.Recommend;

import java.util.List;

public interface RecommendService extends IService<Recommend> {

    //根据用户id获取基于用户推荐的电影
    public List<MoviesScore> getRecommend(int userId);
}
