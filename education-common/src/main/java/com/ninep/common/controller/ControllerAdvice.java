package com.ninep.common.controller;

import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @author NineP
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public Result handleParameterVerificationException(Exception e) {
        log.error(" handleParameterVerificationException has been invoked", e);
        //Map<String, Object> resultMap = new HashMap<>(4);
        //resultMap.put("code", "100001");
        String msg = null;
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            // getFieldError获取的是第一个不合法的参数(P.S.如果有多个参数不合法的话)
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                msg = fieldError.getDefaultMessage();
            }
        } else if (e instanceof BindException) {
            // getFieldError获取的是第一个不合法的参数(P.S.如果有多个参数不合法的话)
            FieldError fieldError = ((BindException) e).getFieldError();
            if (fieldError != null) {
                msg = fieldError.getDefaultMessage();
            }
        } else if (e instanceof ConstraintViolationException) {
            /*
             * ConstraintViolationException的e.getMessage()形如
             *     {方法名}.{参数名}: {message}
             *  这里只需要取后面的message即可
             */
            msg = e.getMessage();
            if (msg != null) {
                int lastIndex = msg.lastIndexOf(':');
                if (lastIndex >= 0) {
                    msg = msg.substring(lastIndex + 1).trim();
                }
            }
            /// ValidationException 的其它子类异常
        } else {
            msg = "处理参数时异常";
        }
        return Result.error(msg);
    }

    @ExceptionHandler(BaseException.class)
    public Result baseError(BaseException e){
        log.error("错误码：{}，错误消息：{}",e.getCode(),e.getMsg());
        return Result.error(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result commonError(Exception e){
        log.error("错误消息：{}",e.getMessage());
        return Result.error(e.getMessage());
    }
}
