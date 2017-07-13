package com.activiti.service.impl;

import com.activiti.service.IUserService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyong on 2017/7/10.
 */
@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private RuntimeService runtimeService;

  @Override
  public String hello() {
    ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("helloProcess");

    System.out.println(processInstance);

    return null;
  }
}
