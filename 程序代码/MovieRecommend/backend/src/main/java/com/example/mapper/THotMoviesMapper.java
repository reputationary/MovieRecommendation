package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.MovieCount;
import com.example.pojo.THotMovies;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface THotMoviesMapper extends BaseMapper<THotMovies> {

    public List<MovieCount> getHotMovies();
}
