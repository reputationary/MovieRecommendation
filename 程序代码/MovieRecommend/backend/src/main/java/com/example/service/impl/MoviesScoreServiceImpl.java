package com.example.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.MoviesScoreMapper;
import com.example.pojo.MoviesScore;
import com.example.service.MoviesScoreService;
import org.springframework.stereotype.Service;

@Service
public class MoviesScoreServiceImpl extends ServiceImpl<MoviesScoreMapper, MoviesScore> implements MoviesScoreService {
}
