package com.ninep.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜单角色关联表
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@TableName("sys_menu_role")
@ApiModel(value = "SysMenuRole对象", description = "菜单角色关联表")
public class SysMenuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty("状态(1:正常，0:禁用)")
    private Byte statusId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("菜单ID")
    private Long menuId;

    @ApiModelProperty("角色ID")
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Byte getStatusId() {
        return statusId;
    }

    public void setStatusId(Byte statusId) {
        this.statusId = statusId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysMenuRole{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", menuId = " + menuId +
            ", roleId = " + roleId +
        "}";
    }
}
