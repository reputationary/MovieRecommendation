package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.pojo.MoviesScore;
import com.example.service.MoviesScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rateTop10")
public class MoviesScoreController {
    @Autowired
    private MoviesScoreService moviesScoreService;

    /**
     * 获取平均评分排名前十的电影
     */
    @GetMapping
    public Result<Page> getTop10Rate(int page, int pageSize){
        //分页构造器
        Page<MoviesScore> pageInfo=new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<MoviesScore> queryWrapper=new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderBy(true,false,MoviesScore::getAvgscore);
        //进行分页查询
        moviesScoreService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }
}
