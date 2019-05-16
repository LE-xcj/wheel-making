package com.xc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chujian
 * @ClassName MyBatisConfig
 * @Description 功能描述
 * @date 2019/5/16 20:54
 */
@Configuration
@MapperScan("com.xc.mapper")
public class MyBatisConfig {
}
    