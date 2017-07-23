package com.activiti.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangyong on 2017/7/16.
 */
@RestController
public class HomeController {

    /**
     * 首页.
     *
     */
    @GetMapping("/")
    final public String index() {
        return "hello,activiti-demo is starting...";
    }

}
