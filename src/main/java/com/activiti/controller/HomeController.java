package com.activiti.controller;

import com.activiti.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangyong on 2017/7/10.
 */
@RestController
public class HomeController {

  @Autowired
  private IUserService userService;

  @RequestMapping("/")
  public String index() {

    userService.hello();
    return "hello,activiti...";
  }

}
