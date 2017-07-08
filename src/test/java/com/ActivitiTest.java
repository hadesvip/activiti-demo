package com;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

/**
 * Created by wangyong on 2017/7/8.
 */
public class ActivitiTest {


  @Test
  public void jodeTimeTest() {

    DateTime dateTime = DateTime.parse("3/2/16", DateTimeFormat.forPattern("m/d/yy"));
    System.out.println(dateTime.toDate());
  }
}
