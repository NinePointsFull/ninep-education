package com.ninep.common.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public final class GeneratorUtil {
    private GeneratorUtil() {
    }

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.0.106:3306/os_user?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.author("NineP") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\NineP\\Desktop\\education\\NineP-Education\\ninep-education\\education-user\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ninep.user") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\NineP\\Desktop\\education\\NineP-Education\\ninep-education\\education-user\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("lecturer","log_login","msg","msg_user","order_info","order_pay","region",
                            "users") ;// 设置需要生成的表名
                    //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
