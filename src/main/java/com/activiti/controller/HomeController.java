package com.activiti.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangyong on 2017/7/10.
 */
@RestController
public class HomeController {

  @RequestMapping("/")
  public String index() {
    return "hello,activiti...";
  }

}
