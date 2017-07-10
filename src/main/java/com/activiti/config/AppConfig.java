package com.activiti.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 入口
 * Created by wangyong on 2017/7/10.
 */
@Configuration
@ComponentScan(basePackages = {"com.activiti"}, useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Service.class, Configuration.class,
        Repository.class, Component.class})})
public class AppConfig {

}
