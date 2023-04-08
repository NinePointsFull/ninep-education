package com.ninep.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.api.course.FeignCourseService;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.common.utils.Result;
import com.ninep.user.admin.DTO.LecturerEditDTO;
import com.ninep.user.admin.DTO.LecturerPageDTO;
import com.ninep.user.admin.DTO.LecturerSaveDTO;
import com.ninep.user.admin.response.LecturerPageResp;
import com.ninep.user.entity.Lecturer;
import com.ninep.user.mapper.LecturerMapper;
import com.ninep.user.service.ILecturerService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
@Slf4j
public class LecturerServiceImpl extends ServiceImpl<LecturerMapper, Lecturer> implements ILecturerService {

    @Autowired
    private FeignCourseService feignCourseService;

    @Override
    public Page<LecturerPageResp> pageList(LecturerPageDTO pageVo) {
        IPage<Lecturer> lecturerPage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<Lecturer> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Lecturer::getSort).orderByDesc(Lecturer::getGmtModified);
        if (StringUtils.hasText(pageVo.getLecturerName())){
            wrapper.like(Lecturer::getLecturerName,pageVo.getLecturerName());
        }
        IPage<Lecturer> page= this.page(lecturerPage, wrapper);
        Page<Lecturer> parse = PageUtil.parse(page);
        Page<LecturerPageResp> transform = PageUtil.transform(parse, LecturerPageResp.class);
        return transform;
    }



    @Override
    public String edit(LecturerEditDTO lecturerEditDTO) {
        Lecturer lecturer = new Lecturer();
        BeanUtil.copyProperties(lecturerEditDTO,lecturer);
        if (this.updateById(lecturer)==true){
            return ResultEnum.SUCCESS.getMsg();
        }
        return ResultEnum.ERROR.getMsg();
    }

    /**
     * 同时删除课程及相关表
     * @param id
     * @return
     */
    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Result<Boolean> result = feignCourseService.deleteByLecturerId(id);
        if (result.getData()==null || result.getData()==false){
            throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
        }
        baseMapper.deleteById(id);
    }

    @Override
    public String save(LecturerSaveDTO lecturerSaveDTO) {
        Lecturer lecturer = BeanUtil.copyProperties(lecturerSaveDTO, Lecturer.class);
        this.save(lecturer);
        return ResultEnum.SUCCESS.getMsg();
    }
}
