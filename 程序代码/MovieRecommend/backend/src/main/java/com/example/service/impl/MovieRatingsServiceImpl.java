package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.MovieRatingsMapper;
import com.example.pojo.MovieRatings;
import com.example.pojo.MyMovies;
import com.example.service.MovieRatingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieRatingsServiceImpl extends ServiceImpl<MovieRatingsMapper, MovieRatings> implements MovieRatingsService {
    @Resource
    private MovieRatingsMapper mapper;

    @Override
    public List<MyMovies> getMyMovies(int userId,int total,int page,int pageSize) {
        int begin=(page-1)*pageSize;

        return mapper.getMyMovies(userId,begin,pageSize);
    }
}
