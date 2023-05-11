package com.example;

import com.example.mapper.THotMoviesMapper;
import com.example.pojo.MovieCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private THotMoviesMapper mapper;

    @Test
    void contextLoads() {
//        List<MovieCount> hotMovies = mapper.getHotMovies();
//        System.out.println(hotMovies);
    }

}
