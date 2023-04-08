package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.course.admin.DTO.ZoneCourseEditDTO;
import com.ninep.course.admin.DTO.ZoneCoursePageDTO;
import com.ninep.course.admin.DTO.ZoneCourseSaveDTO;
import com.ninep.course.entity.Course;
import com.ninep.course.entity.ZoneCourse;
import com.ninep.course.mapper.CourseMapper;
import com.ninep.course.mapper.ZoneCourseMapper;
import com.ninep.course.admin.response.CourseViewResp;
import com.ninep.course.admin.response.ZoneCoursePageResp;
import com.ninep.course.service.IZoneCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 专区课程关联表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class ZoneCourseServiceImpl extends ServiceImpl<ZoneCourseMapper, ZoneCourse> implements IZoneCourseService {
    @Resource
    private CourseMapper courseMapper;

    @Override
    public Page<ZoneCoursePageResp> pageList(ZoneCoursePageDTO pageVo) {
        IPage<ZoneCourse> zoneCourseIPage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<ZoneCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(ZoneCourse::getSort).orderByDesc(ZoneCourse::getGmtModified).
                //获取专区课程
                eq(ZoneCourse::getZoneId,pageVo.getZoneId());
        this.page(zoneCourseIPage,wrapper);
        Page<ZoneCourse> parse = PageUtil.parse(zoneCourseIPage);
        Page<ZoneCoursePageResp> transform = PageUtil.transform(parse, ZoneCoursePageResp.class);
        if (!CollectionUtils.isEmpty(transform.getList())){
            List<Long> courseIds = transform.getList().stream().map(ZoneCoursePageResp::getCourseId).collect(Collectors.toList());
            Map<Long, Course> courseMaps = courseMapper.selectList(new LambdaQueryWrapper<Course>().in(Course::getId, courseIds)).stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
            if (!MapUtil.isEmpty(courseMaps)){
                for (ZoneCoursePageResp zoneCoursePageResp:transform.getList()){
                    CourseViewResp courseViewResp = BeanUtil.copyProperties(courseMaps.get(zoneCoursePageResp.getCourseId()), CourseViewResp.class);
                    zoneCoursePageResp.setCourseViewResp(courseViewResp);
                }
            }
        }
        return transform;
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'zoneCourse'")
    public String saveRelation(ZoneCourseSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, ResultEnum.OTHER_ERROR);
        ZoneCourse zoneCourse = BeanUtil.copyProperties(saveVo, ZoneCourse.class);
        boolean save = this.save(zoneCourse);
        return zoneCourseResult(save);
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'zoneCourse'")
    public String delete(Long id) {
        boolean b = this.removeById(id);
        return zoneCourseResult(b);
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'zoneCourse'")
    public String edit(ZoneCourseEditDTO editVo) {
        AssertUtil.notNull(editVo,ResultEnum.OTHER_ERROR);
        AssertUtil.notNull(editVo.getId(),ResultEnum.OTHER_ERROR);
        ZoneCourse zoneCourse = BeanUtil.copyProperties(editVo, ZoneCourse.class);
        boolean b = this.updateById(zoneCourse);
        return zoneCourseResult(b);
    }

    private static String zoneCourseResult(boolean save) {
        if (save){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }
}
