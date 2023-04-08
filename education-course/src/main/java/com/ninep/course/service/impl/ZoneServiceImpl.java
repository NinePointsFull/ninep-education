package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.course.admin.response.ZonePageResp;
import com.ninep.course.admin.DTO.ZoneEditDTO;
import com.ninep.course.admin.DTO.ZonePageDTO;
import com.ninep.course.admin.DTO.ZoneSaveDTO;
import com.ninep.course.api.response.ApiZoneCourseResp;
import com.ninep.course.api.response.ApiZoneResp;
import com.ninep.course.entity.Course;
import com.ninep.course.entity.Zone;
import com.ninep.course.entity.ZoneCourse;
import com.ninep.course.mapper.CourseMapper;
import com.ninep.course.mapper.ZoneCourseMapper;
import com.ninep.course.mapper.ZoneMapper;
import com.ninep.course.service.IZoneService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 专区 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class ZoneServiceImpl extends ServiceImpl<ZoneMapper, Zone> implements IZoneService {
    @Resource
    private ZoneCourseMapper zoneCourseMapper;
    @Resource
    private CourseMapper courseMapper;

    /**
     * @param pageVo
     * @return
     */
    @Override
    public Page<ZonePageResp> pageList(ZonePageDTO pageVo) {
        IPage<Zone> zonePage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<Zone> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Zone::getSort).orderByDesc(Zone::getGmtModified);
        if (StringUtils.hasText(pageVo.getZoneName())){
            wrapper.like(Zone::getZoneName,pageVo.getZoneName());
        }
        this.page(zonePage,wrapper);
        Page<Zone> parse = PageUtil.parse(zonePage);
        Page<ZonePageResp> transform = PageUtil.transform(parse, ZonePageResp.class);
        return transform;
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'zoneCourse'")
    public String saveZone(ZoneSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, ResultEnum.OTHER_ERROR);
        Zone zone = BeanUtil.copyProperties(saveVo, Zone.class);
        boolean save = this.save(zone);
        return zoneResult(save);
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'zoneCourse'")
    public String edit(ZoneEditDTO zonEditDTO) {
        AssertUtil.notNull(zonEditDTO,ResultEnum.OTHER_ERROR);
        AssertUtil.notNull(zonEditDTO.getId(),ResultEnum.OTHER_ERROR);
        Zone zone = BeanUtil.copyProperties(zonEditDTO, Zone.class);
        boolean b = this.updateById(zone);

        return zoneResult(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "course",key = "'zoneCourse'")
    public String delete(Long id) {
        //同时删除专区下的课程
        zoneCourseMapper.delete(new LambdaQueryWrapper<ZoneCourse>().eq(ZoneCourse::getZoneId,id));
        boolean b = this.removeById(id);
        return zoneResult(b);
    }

    @Override
    @Cacheable(cacheNames = "course",key = "'zoneCourse'",sync = true)
    public List<ApiZoneResp> listForZone() {
        //获取所有专区
        List<Zone> zones = this.list(new LambdaQueryWrapper<Zone>().eq(Zone::getStatusId, StatusIdEnum.YES.getCode()));
        List<ApiZoneResp> apiZoneResps = BeanUtil.copyToList(zones, ApiZoneResp.class);
        if (!apiZoneResps.isEmpty()){
            //获取所有专区所有课程
            extracted(apiZoneResps);
        }

        return apiZoneResps;
    }

    /**
     * 封装
     * @param apiZoneResps
     */
    private void extracted(List<ApiZoneResp> apiZoneResps) {
        List<Long> zoneIds = apiZoneResps.stream().map(ApiZoneResp::getId).collect(Collectors.toList());
        List<ZoneCourse> zoneCourses = zoneCourseMapper.selectList(new LambdaQueryWrapper<ZoneCourse>().eq(ZoneCourse::getStatusId,StatusIdEnum.YES.getCode()).in(ZoneCourse::getZoneId, zoneIds));
        //获取所有专区课程
        Map<Long, Course> courseMap = courseMapper.selectList(new LambdaQueryWrapper<Course>().in(Course::getId, zoneCourses.stream().map(ZoneCourse::getCourseId).collect(Collectors.toList()))).
                stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
        Map<Long, List<ZoneCourse>> map = zoneCourses.stream().collect(Collectors.groupingBy(ZoneCourse::getZoneId, Collectors.toList()));
        for (ApiZoneResp apiZoneResp:apiZoneResps){
            List<ApiZoneCourseResp> apiZoneCourseResps = new ArrayList<>();
            Long zoneId = apiZoneResp.getId();
            List<ZoneCourse> zoneCourseList = map.get(zoneId);
            if (!zoneCourseList.isEmpty()){
                for (ZoneCourse zoneCourse:zoneCourseList){
                    ApiZoneCourseResp apiZoneCourseResp = new ApiZoneCourseResp();
                    Course course = courseMap.get(zoneCourse.getCourseId());
                    BeanUtil.copyProperties(course,apiZoneCourseResp);
                    apiZoneCourseResps.add(apiZoneCourseResp);
                }
            }
            apiZoneResp.setCourseList(apiZoneCourseResps);
        }
    }

    private static String zoneResult(boolean save) {
        if (save){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }
}
