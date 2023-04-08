package com.ninep.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.kisso.enums.TokenOrigin;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.SysLoginEnum;
import com.ninep.common.utils.*;
import com.ninep.system.admin.dto.*;
import com.ninep.system.admin.response.SysLoginResp;
import com.ninep.system.admin.response.SysUserPageResp;
import com.ninep.system.admin.response.SysUserViewResp;
import com.ninep.system.entity.*;
import com.ninep.system.mapper.*;
import com.ninep.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    SysMenuRoleMapper sysMenuRoleMapper;
    @Override
    public SysLoginResp login(SysLoginDTO sysLoginDTO) {
        String mobile= sysLoginDTO.getMobile();
        String password= sysLoginDTO.getMobilePwd();
        //获取当前用户
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile, mobile));
        //是否存在
        AssertUtil.notNull(sysUser,SysLoginEnum.USER_NOT_EXIST);
        //校验状态
        AssertUtil.statusEnable(sysUser.getStatusId(),SysLoginEnum.USER_STATUS_DISABLE);
        //校验密码
        String mobileSalt = sysUser.getMobileSalt();
        String hashpw = BCrypt.hashpw(password, mobileSalt);
        log.info("密码{}",hashpw);
        AssertUtil.passwordSuccess(sysUser.getMobilePsw(),hashpw,SysLoginEnum.USER_PASSWORD_ERROR);

        SysLoginResp sysLoginResp = new SysLoginResp();
        sysLoginResp.setMobile(sysUser.getMobile());
        sysLoginResp.setRealName(sysUser.getRealName());
        //生成令牌
        String jwt = SSOToken.create().setId(sysUser.getId()).setIssuer("NineP").setOrigin(TokenOrigin.HTML5).getToken();;
        log.info("jwt:{}",jwt);
        sysLoginResp.setToken(jwt);

        //将令牌放入缓存
        stringRedisTemplate.opsForValue().set(ConstantsUtil.RedisPre.USERS_INFO.concat(sysUser.getId().toString()), jwt, 1, TimeUnit.DAYS);
        // 获取菜单权限，放入缓存
        List<String> menus = extracted(sysUser);
        stringRedisTemplate.opsForValue().set(ConstantsUtil.RedisPre.ADMINI_MENU.concat(sysUser.getId().toString()), JSUtil.toJsonString(menus), 1, TimeUnit.DAYS);
        //TODO 添加登入日志
        return sysLoginResp;
    }

    @Override
    public Result<SysUserViewResp> view(SysUserViewDTO sysUserViewDTO) {
        if (sysUserViewDTO.getId() == null) {
            return Result.error("主键ID不能为空");
        }
        SysUser sysUser = baseMapper.selectById(sysUserViewDTO.getId());
        if (ObjectUtil.isNull(sysUser)) {
            return Result.error("管理员不存在");
        }
        return Result.success(BeanUtil.copyProperties(sysUser, SysUserViewResp.class));
    }

    @Override
    public Result<String> updatePassword(SysUserUpdatePasswordDTO sysUserUpdatePasswordDTO) {
        if (sysUserUpdatePasswordDTO.getUserId() == null) {
            return Result.error("用户ID不能为空,请重试");
        }
        if (StringUtils.isEmpty(sysUserUpdatePasswordDTO.getMobilePwd())) {
            return Result.error("新密码不能为空,请重试");
        }
        if (StringUtils.isEmpty(sysUserUpdatePasswordDTO.getConfirmPassword())) {
            return Result.error("确认密码不能为空,请重试");
        }
        if (!sysUserUpdatePasswordDTO.getConfirmPassword().equals(sysUserUpdatePasswordDTO.getMobilePwd())) {
            return Result.error("密码不一致,请重试");
        }
        SysUser record = new SysUser();
        record.setId(sysUserUpdatePasswordDTO.getUserId());
        String gensalt = BCrypt.gensalt();
        record.setMobileSalt(gensalt);
        record.setMobilePsw(BCrypt.hashpw(sysUserUpdatePasswordDTO.getConfirmPassword(),gensalt));
        baseMapper.updateById(record);
        return Result.success("操作成功");
    }

    @Override
    public Page<SysUserPageResp> listForPage(SysUserPageDTO sysUserPageDTO) {
        IPage<SysUser> page=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(sysUserPageDTO.getPageCurrent(), sysUserPageDTO.getPageSize());
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysUser::getSort).orderByDesc(SysUser::getGmtModified);
        if (StringUtils.hasText(sysUserPageDTO.getMobile())){
            wrapper.like(SysUser::getMobile, sysUserPageDTO.getMobile());
        }
        this.page(page,wrapper);
        Page<SysUser> parse = PageUtil.parse(page);
        Page<SysUserPageResp> transform = PageUtil.transform(parse, SysUserPageResp.class);
        //封装page
        successPage(transform);
        return transform;
    }

    @Override
    public Result<String> save(SysUserSaveDTO sysUserSaveDTO) {
        if (!sysUserSaveDTO.getMobilePwd().equals(sysUserSaveDTO.getRePassword())) {
            return Result.error("密码不一致");
        }
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getMobile,sysUserSaveDTO.getMobile());
        SysUser sysUser = this.getOne(wrapper);
        if (ObjectUtil.isNotNull(sysUser)) {
            return Result.error("用户已添加成管理员");
        }
        SysUser record = BeanUtil.copyProperties(sysUserSaveDTO, SysUser.class);
        String gensalt = BCrypt.gensalt();
        record.setMobileSalt(gensalt);
        record.setMobilePsw(BCrypt.hashpw(sysUserSaveDTO.getRePassword(),gensalt));
        int results = baseMapper.insert(record);
        if (results > 0) {
            return Result.success("操作成功");
        }
        return Result.error(ResultEnum.OTHER_ERROR.getMsg());
    }

    @Override
    public Result<String> delete(SysUserDeleteDTO sysUserDeleteDTO) {
        if (sysUserDeleteDTO.getId() == null) {
            return Result.error("主键ID不能为空");
        }
        // 1、删除用户所有角色
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId,sysUserDeleteDTO.getId());
        sysRoleUserMapper.delete(wrapper);
        // 2、删除用户
        baseMapper.deleteById(sysUserDeleteDTO.getId());
        return Result.success("操作成功");
    }

    @Override
    public Result<String> update(SysUserUpdateDTO sysUserUpdateDTO) {
        if (sysUserUpdateDTO.getId() == null) {
            return Result.error("主键ID不能为空");
        }
        SysUser sysUser = baseMapper.selectById(sysUserUpdateDTO.getId());
        if (ObjectUtil.isNull(sysUser)) {
            return Result.error("找不到管理员信息");
        }
        SysUser record = BeanUtil.copyProperties(sysUserUpdateDTO, SysUser.class);
        int results = baseMapper.updateById(record);
        if (results > 0) {
            return Result.success("操作成功");
        }
        return Result.error(ResultEnum.OTHER_ERROR.getMsg());
    }


    private void successPage(Page<SysUserPageResp> transform) {
        if (!transform.getList().isEmpty()){
            //获取所有roleUser
            List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(null);
            //一个userId对应多个roleId
            Map<Long,List<Long>> roleUserMap=new HashMap<>();
            if (!sysRoleUsers.isEmpty()){
                roleUserMap= sysRoleUsers.stream().collect(Collectors.groupingBy(SysRoleUser::getUserId, Collectors.mapping(SysRoleUser::getRoleId, Collectors.toList())));
            }
            //获取所有role
            List<SysRole> sysRoleList = sysRoleMapper.selectList(null);
            Map<Long,String> roleMap=new HashMap<>();
            if (!sysRoleList.isEmpty()){
                roleMap=sysRoleList.stream().collect(Collectors.toMap(SysRole::getId,SysRole::getRoleName,(item1,item2)->item1));
            }

            for (SysUserPageResp sysUserPageResp:transform.getList()){
                List<String> names = new ArrayList<>();
                Long userId = sysUserPageResp.getId();
                //获取当前userId下的所有role
                List<Long> roleIds = roleUserMap.get(userId);
                if (roleIds!=null && !roleIds.isEmpty()){
                    for (Long roleId:roleIds){
                        String name = roleMap.get(roleId);
                        names.add(name);
                    }
                }
                sysUserPageResp.setRoleNameList(names);
            }
        }
    }
    private List<String> extracted(SysUser sysUser) {
        //获取当前用户的所有权限
        List<SysRoleUser> roleUsers = sysRoleUserMapper.selectList(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId,sysUser.getId()));
        if (CollectionUtil.isEmpty(roleUsers)) {
            return new ArrayList<>();
        }
        //获取当前用户能使用的所有菜单
        List<Long> roleIds = roleUsers.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());
        List<SysMenuRole> menuRoles = sysMenuRoleMapper.selectList(new LambdaQueryWrapper<SysMenuRole>().in(SysMenuRole::getRoleId,roleIds));
        if (CollectionUtil.isEmpty(menuRoles)) {
            return new ArrayList<>();
        }
        //列出所有菜单
        List<Long> menuIds = menuRoles.stream().map(SysMenuRole::getMenuId).collect(Collectors.toList());
        List<SysMenu> sysMenus = sysMenuMapper.selectBatchIds(menuIds);
        if (CollectionUtil.isEmpty(sysMenus)) {
            return new ArrayList<>();
        }
        return sysMenus.stream().map(SysMenu::getAuthValue).collect(Collectors.toList());
    }
}
