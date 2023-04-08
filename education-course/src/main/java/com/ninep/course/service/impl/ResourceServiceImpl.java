package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.api.system.FeignSystemService;
import com.ninep.common.DTO.VodDTO;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.enums.VodPlatformEnum;
import com.ninep.common.utils.*;
import com.ninep.course.admin.DTO.ResourceEditDTO;
import com.ninep.course.admin.DTO.ResourcePageDTO;
import com.ninep.course.admin.response.ResourcePageResp;
import com.ninep.course.admin.response.VodConfigResp;
import com.ninep.course.admin.DTO.ResourceSaveDTO;
import com.ninep.course.entity.CourseChapterPeriod;
import com.ninep.course.entity.Resource;
import com.ninep.course.mapper.CourseChapterPeriodMapper;
import com.ninep.course.mapper.ResourceMapper;
import com.ninep.course.mapper.UserStudyMapper;
import com.ninep.course.service.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
@Slf4j
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @javax.annotation.Resource
    private CourseChapterPeriodMapper courseChapterPeriodMapper;

    @javax.annotation.Resource
    private FeignSystemService feignSystemService;
    @javax.annotation.Resource
    private UserStudyMapper userStudyMapper;

    @Override
    public Page<ResourcePageResp> pageList(ResourcePageDTO pageVo) {
        IPage<Resource> page=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Resource::getSort).orderByDesc(Resource::getGmtModified);
        if (StringUtils.hasText(pageVo.getResourceName())){
            wrapper.like(Resource::getResourceName,pageVo.getResourceName());
        }
        this.page(page,wrapper);
        Page<Resource> parse = PageUtil.parse(page);
        Page<ResourcePageResp> transform = PageUtil.transform(parse, ResourcePageResp.class);
        return transform;
    }

    @Override
    public Result<String>edit(ResourceEditDTO editVo) {
        AssertUtil.notNull(editVo, SysConfigEnum.CONFIG_ERROR);
        AssertUtil.notNull(editVo.getId(),SysConfigEnum.CONFIG_ERROR);
        Resource resource = BeanUtil.copyProperties(editVo, Resource.class);
        return resourceResult(this.updateById(resource));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> delete(Long id) {
        //获取课时id
        List<CourseChapterPeriod> courseChapterPeriods = courseChapterPeriodMapper.selectList(new LambdaQueryWrapper<CourseChapterPeriod>().eq(CourseChapterPeriod::getResourceId, id));
        //根据课时id删除学习记录
        if (CollUtil.isNotEmpty(courseChapterPeriods)){
            userStudyMapper.deleteBatchIds(courseChapterPeriods);
        }
        //删除对应课时
        courseChapterPeriodMapper.delete(new LambdaQueryWrapper<CourseChapterPeriod>().eq(CourseChapterPeriod::getResourceId,id));

        this.removeById(id);
        return Result.success("删除成功");
    }

    @Override
    public Result<VodConfigResp> getVodConfig() {
        //获取配置
        VodDTO vod = feignSystemService.vod();
        VodConfigResp resp = new VodConfigResp();
        resp.setVodPlatform(vod.getVodPlatform());
        if (VodPlatformEnum.POLYV.getCode().equals(vod.getVodPlatform())){
            polyv(resp,vod);
        }else if (VodPlatformEnum.TENCENT.getCode().equals(vod.getVodPlatform())){
            tencent(resp,vod);
        }
        //TODO
        return Result.success(resp);
    }

    private void tencent(VodConfigResp resp, VodDTO vod) {
        //VodUploadClient vodUploadClient = TencentUtil.getVodUploadClient(vod.getTencentkey(), vod.getTencentSecret());
        //TencentUtil.upload(vodUploadClient);
    }

    private void polyv(VodConfigResp resp, VodDTO vod) {
        resp.setPolyvConfig(getCofigByPolyv(vod));
    }


    @Override
    public Result<String> saveResource(ResourceSaveDTO saveVo) {
        //TODO

        return null;
    }

    /**
     * 获取保利威上传参数
     *
     * @param vodConfig
     * @return
     */
    private VodConfigResp.PolyvConfig getCofigByPolyv(VodDTO vodConfig) {
        VodConfigResp.PolyvConfig polyvConfig = new VodConfigResp.PolyvConfig();
        polyvConfig.setUserid(vodConfig.getPolyvUserId());
        polyvConfig.setPtime(System.currentTimeMillis());
        polyvConfig.setSign(MD5Util.md5(vodConfig.getPolyvSecretKey() + polyvConfig.getPtime()));
        polyvConfig.setHash(MD5Util.md5(polyvConfig.getPtime() + vodConfig.getPolyvWriteToken()));
        return polyvConfig;
    }

    private Result<String> resourceResult(boolean id) {
        if (id){
            return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
        }
        return Result.success(ResultEnum.OTHER_ERROR.getMsg());
    }
}
