package com.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

/**
 * Created by wangyong on 2017/7/9.
 */
public class MyEventListener implements ActivitiEventListener {

  @Override
  public void onEvent(ActivitiEvent event) {
    switch (event.getType()) {
      //作业执行成功
      case JOB_EXECUTION_SUCCESS:
        System.out.println("a job well done");
        break;
      //作业执行失败
      case JOB_EXECUTION_FAILURE:
        System.out.println("A job has failed...");
        break;
      default:
        System.out.println("Event received:" + event.getType());
    }

  }

  @Override
  public boolean isFailOnException() {
    return false;
  }
}
