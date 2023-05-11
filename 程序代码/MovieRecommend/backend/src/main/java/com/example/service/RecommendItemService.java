package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.MoviesScore;
import com.example.pojo.RecommendItem;

import java.util.List;

public interface RecommendItemService extends IService<RecommendItem> {

    //根据电影id，推荐与此电影相似的电影
    public List<MoviesScore> getRecommend(int movieId);
}
