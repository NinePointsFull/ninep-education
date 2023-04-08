package com.ninep.common.exception;

import com.ninep.common.enums.ResultEnum;
import lombok.Getter;

/**
 * @author NineP
 */
@Getter
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;

    public BaseException(String message) {
        super(message);
        this.msg=message;
        this.code = 99;
    }
    public BaseException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
        this.msg=resultEnum.getMsg();
    }
    public BaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, Throwable cause, Integer code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(Throwable cause, Integer code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }
}
