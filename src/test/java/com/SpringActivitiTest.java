package com;

import com.activiti.bean.UserBean;
import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * activiti跟spring整合测试
 * Created by wangyong on 2017/7/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring/spring-*.xml"})
public class SpringActivitiTest {

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private UserBean userBean;

  @Test
  public void test() {
    String id = repositoryService
        .createDeployment()
        .addClasspathResource("activiti/hello.bpmn20.xml")
        .deploy()
        .getId();
    System.out.println(id);
    userBean.hello();
  }


}
