package com.activiti.script;

import java.util.Date;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * Created by wangyong on 2017/7/8.
 */
public class AutomatedDataDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    execution.setVariable("autoWelcomeTime", new Date());
    System.out.println("Faux call to backend for ["
        + execution.getVariable("fullName") + "]");

  }
}
