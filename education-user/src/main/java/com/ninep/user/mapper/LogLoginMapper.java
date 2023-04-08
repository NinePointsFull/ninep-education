package com.ninep.user.mapper;

import cn.hutool.core.date.DateTime;
import com.ninep.user.admin.response.StatLogin;
import com.ninep.user.entity.LogLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户登录日志 Mapper 接口
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface LogLoginMapper extends BaseMapper<LogLogin> {

    @Select("select DATE_FORMAT(gmt_create, '%Y-%m-%d') as dates, count(*) as logins, login_status from log_login where login_status>0 and gmt_create>#{date} GROUP BY dates,login_status order by dates asc")
    List<StatLogin> statByDate(@Param("date") DateTime dates);
}
