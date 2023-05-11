package com.example.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回结果类，服务端响应的数据最终都会封装成此对象
 */
@Data
public class Result<T> {

    private Integer code; //编码：1成功，0和其他数字为失败
    private String msg; //错误信息
    private T data;
    private Map map=new HashMap(); //动态数据

    public static <T> Result<T> success(T object){
        Result<T> result=new Result<>();
        result.data=object;
        result.code=1;
        return result;
    }

    public static <T> Result<T> error(String msg){
        Result result=new Result();
        result.msg=msg;
        result.code=0;
        return result;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
