package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RecommendMapper;
import com.example.pojo.MoviesScore;
import com.example.pojo.Recommend;
import com.example.service.MoviesScoreService;
import com.example.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {
    @Autowired
    private MoviesScoreService moviesScoreService;

    /**
     * 根据用户id获取基于用户推荐的电影
     */
    @Override
    @Transactional
    public List<MoviesScore> getRecommend(int userId) {
        LambdaQueryWrapper<Recommend> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Recommend::getUserId,userId);
        Recommend recommend = this.getOne(queryWrapper);
        String recommends = recommend.getRecommends();
        String[] strings = recommends.split(",");
        List<Integer> recommendList = Arrays.stream(strings).map(item -> {
            return Integer.parseInt(item);
        }).collect(Collectors.toList());
        List<MoviesScore> moviesScores = moviesScoreService.listByIds(recommendList);

        return moviesScores;
    }
}
