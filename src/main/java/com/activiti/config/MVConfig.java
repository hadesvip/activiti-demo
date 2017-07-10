package com.activiti.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wangyong on 2017/7/10.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.activiti", useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(RestController.class), @ComponentScan.Filter(Controller.class)})
public class MVConfig extends WebMvcConfigurerAdapter {


}
