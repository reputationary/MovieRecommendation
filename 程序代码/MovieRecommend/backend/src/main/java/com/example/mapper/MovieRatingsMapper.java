package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.MovieRatings;
import com.example.pojo.MyMovies;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieRatingsMapper extends BaseMapper<MovieRatings> {
    public List<MyMovies> getMyMovies(@Param("userId") int userId, @Param("begin")int begin, @Param("pageSize")int pageSize);
}
