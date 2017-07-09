package com.activiti.service;

import org.springframework.stereotype.Component;

/**
 * Created by wangyong on 2017/7/9.
 */
@Component("printer")
public class PrinterService {

  public void printMessage() {
    System.out.println("hello,world...");
  }

}
