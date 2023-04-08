package com.ninep.user.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 行政区域表
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@ApiModel(value = "Region对象", description = "行政区域表")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("父id")
    private Long parentId;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("区域编码（国标）")
    private String provinceCode;

    @ApiModelProperty("中心经度")
    private BigDecimal centerLng;

    @ApiModelProperty("中心维度")
    private BigDecimal centerLat;

    @ApiModelProperty("省Id")
    private Integer provinceId;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市Id")
    private Integer cityId;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区域名称")
    private String regionName;

    @ApiModelProperty("区名称")
    private String districtName;

    @ApiModelProperty("全路径名称")
    private String mergerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public BigDecimal getCenterLng() {
        return centerLng;
    }

    public void setCenterLng(BigDecimal centerLng) {
        this.centerLng = centerLng;
    }

    public BigDecimal getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(BigDecimal centerLat) {
        this.centerLat = centerLat;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    @Override
    public String toString() {
        return "Region{" +
            "id = " + id +
            ", parentId = " + parentId +
            ", level = " + level +
            ", provinceCode = " + provinceCode +
            ", centerLng = " + centerLng +
            ", centerLat = " + centerLat +
            ", provinceId = " + provinceId +
            ", provinceName = " + provinceName +
            ", cityId = " + cityId +
            ", cityCode = " + cityCode +
            ", cityName = " + cityName +
            ", regionName = " + regionName +
            ", districtName = " + districtName +
            ", mergerName = " + mergerName +
        "}";
    }
}
