package com.activiti.bean;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangyong on 2017/7/9.
 */
//@Component
public class UserBean {

  @Autowired
  private RuntimeService runtimeService;

  @Transactional
  public void hello() {
    runtimeService.startProcessInstanceByKey("helloProcess");
  }
}
