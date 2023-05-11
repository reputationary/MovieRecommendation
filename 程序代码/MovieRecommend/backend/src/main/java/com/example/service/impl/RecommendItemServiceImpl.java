package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RecommendItemMapper;
import com.example.pojo.MoviesScore;
import com.example.pojo.RecommendItem;
import com.example.service.MoviesScoreService;
import com.example.service.RecommendItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendItemServiceImpl extends ServiceImpl<RecommendItemMapper, RecommendItem> implements RecommendItemService {
    @Autowired
    private MoviesScoreService moviesScoreService;

    /**
     * 根据电影id，推荐与此电影相似的电影
     */
    @Override
    @Transactional
    public List<MoviesScore> getRecommend(int movieId) {
        LambdaQueryWrapper<RecommendItem> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(RecommendItem::getMovie_id,movieId);
        RecommendItem recommendItem = this.getOne(queryWrapper);
        String s = recommendItem.getRecommends();
        String[] strings = s.split(",");
        List<Integer> collect = Arrays.stream(strings).map(item -> {
            return Integer.parseInt(item);
        }).collect(Collectors.toList());
        List<MoviesScore> moviesScores = moviesScoreService.listByIds(collect);
        return moviesScores;
    }
}
