package com.ninep.common.utils;

import com.ninep.common.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 返回前端结果
 * @param <T>
 */
@Data
@Slf4j
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;


    /**
     * 返回结果
     */
    private T data;

    public static <T> Result<T> success(T data){
        return new Result<T>(ResultEnum.SUCCESS.getCode(), "",data);
    }

    public static <T> Result<T> error(String msg){
        log.debug("返回错误：code={}, msg={}", ResultEnum.ERROR.getCode(), msg);
        return new Result<T>(ResultEnum.ERROR.getCode(), msg,null);
    }

    public static<T> Result<T> error(Integer code,String msg){
        return new Result<T>(code,msg,null);
    }
}
