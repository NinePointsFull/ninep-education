package com.ninep.common.DTO;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author NineP
 */
@Data
@Slf4j
@Document(indexName = CourseEsDTO.COURSE)
public class CourseEsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String COURSE = "ninep_es_course";
    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程封面
     */
    private String courseLogo;
    /**
     * 分类
     */
    private Long categoryId;
    /**
     * 是否免费(1:免费，0:收费)
     */
    private Integer isFree;
    /**
     * 原价
     */
    private BigDecimal rulingPrice;
    /**
     * 优惠价
     */
    private BigDecimal coursePrice;
    /**
     * 课程排序
     */
    private Integer courseSort;
}
