package com.ninep.common.utils;

import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.enums.SysLoginEnum;
import com.ninep.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author NineP
 */
@Slf4j
public final class AssertUtil extends Assert {
    private AssertUtil(){

    }

    public static void notNull(Object o, SysLoginEnum sysLoginEnum){
        if (o==null){
            throw new BaseException(sysLoginEnum.getCode(),sysLoginEnum.getMsg());
        }
    }

    public static void notTrue(Boolean notTrue, String msg){
        if (notTrue){
            throw new BaseException(msg);
        }
    }
    public static void notNull(Object o, String msg){
        if (o==null){
            throw new BaseException(msg);
        }
    }

    public static void notNull(Object o, Integer code,String msg){
        if (o==null){
            throw new BaseException(code,msg);
        }
    }


    public static void notNull(Object o, ResultEnum resultEnum){
        if (o==null){
            throw new BaseException(resultEnum.getCode(),resultEnum.getMsg());
        }
    }

    public static void notNull(Object o, SysConfigEnum sysConfigEnum){
        if (o==null){
            throw new BaseException(sysConfigEnum.getCode(),sysConfigEnum.getMsg());
        }
    }

    public static void statusEnable(Integer status, SysLoginEnum sysLoginEnum){
        if (status!=1){
            throw new BaseException(sysLoginEnum.getCode(),sysLoginEnum.getMsg());
        }
    }

    public static void passwordSuccess(String password1,String password2, SysLoginEnum sysLoginEnum){
        if (!password1.equals(password2)){
            throw new BaseException(sysLoginEnum.getCode(),sysLoginEnum.getMsg());
        }
    }

    public static void notEmpty(@NotNull List o, SysConfigEnum sysConfigEnum){
        if (o.isEmpty()){
            throw new BaseException(sysConfigEnum.getCode(),sysConfigEnum.getMsg());
        }
    }

    public static void isTrue(@NotNull Boolean isTrue, Integer code,String msg){
        if (isTrue==false){
            throw new BaseException(code,msg);
        }
    }

    public static void isTrue(@NotNull Boolean isTrue,String msg){
        if (isTrue==false){
            throw new BaseException(msg);
        }
    }

}
