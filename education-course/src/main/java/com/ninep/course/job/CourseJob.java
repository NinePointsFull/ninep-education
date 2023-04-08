package com.ninep.course.job;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ninep.common.DTO.CourseEsDTO;
import com.ninep.common.enums.PutawayEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.course.entity.Course;
import com.ninep.course.service.ICourseService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class CourseJob {
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 每天凌晨5点执行一次
     * 同步数据库课程到es
     */
    @XxlJob("courseJobHandler")
    public void course() {
        //获取上架的课程
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatusId,StatusIdEnum.YES.getCode()).eq(Course::getIsPutaway,PutawayEnum.UP.getCode());
        List<Course> courseList = courseService.list(wrapper);
        if (CollUtil.isNotEmpty(courseList)) {
            List<IndexQuery> queries = new ArrayList<>();
            for (Course course : courseList) {
                CourseEsDTO courseEsDTO = BeanUtil.copyProperties(course, CourseEsDTO.class);
                IndexQuery query = new IndexQueryBuilder().withObject(courseEsDTO).build();
                queries.add(query);
            }
            elasticsearchRestTemplate.indexOps(CourseEsDTO.class).delete();
            elasticsearchRestTemplate.bulkIndex(queries, IndexCoordinates.of(CourseEsDTO.COURSE));
        }
        XxlJobHelper.handleSuccess("完成");
    }

    @XxlJob("testJobHandler")
    public void test(){
        log.info("test=>>>>>>>>>>>>>>>");
    }
}
