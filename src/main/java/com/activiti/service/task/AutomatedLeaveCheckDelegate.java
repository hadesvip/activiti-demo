package com.activiti.service.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 自动执行请假检查
 * Created by wangyong on 2017/7/13.
 */
public class AutomatedLeaveCheckDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {

    System.out.println(execution.getVariable("approved"));



    System.out.println("执行请假自动检查...");

  }
}
