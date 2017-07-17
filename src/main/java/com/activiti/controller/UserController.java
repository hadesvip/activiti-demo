package com.activiti.controller;

import com.activiti.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by wangyong on 2017/7/16.
 */
@RestController
public class UserController {

  @GetMapping("/getUser")
  public User getUser(String userName) {
    return new User(UUID.randomUUID().toString().replace("-", ""), "张三");
  }
}
