package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.THotMoviesMapper;
import com.example.pojo.MovieCount;
import com.example.pojo.THotMovies;
import com.example.service.THotMoviesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class THotMoviesServiceImpl extends ServiceImpl<THotMoviesMapper, THotMovies> implements THotMoviesService {
    @Resource
    private THotMoviesMapper mapper;

    @Override
    public List<MovieCount> getHotMovies() {
        return mapper.getHotMovies();
    }
}
