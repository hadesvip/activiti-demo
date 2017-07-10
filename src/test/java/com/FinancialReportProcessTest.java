package com;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * Created by wangyong on 2017/7/10.
 */

public class FinancialReportProcessTest {


  @Test
  public void test() throws InterruptedException {

    //配置
    ProcessEngineConfiguration engineConfiguration = new StandaloneInMemProcessEngineConfiguration();
    engineConfiguration
        .setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useSSL=false")
        .setJdbcUsername("root")
        .setJdbcPassword("root")
        .setJdbcDriver("com.mysql.jdbc.Driver")
        .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

    //流程引擎
    ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
    RepositoryService repositoryService = processEngine.getRepositoryService();

    Deployment deployment = repositoryService.createDeployment()
        .addClasspathResource("activiti/financialReportProcess.bpmn20.xml").deploy();

    RuntimeService runtimeService = processEngine.getRuntimeService();
    ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("financialReport");
    System.out.println(processInstance);

    //任务列表
    TaskService taskService = processEngine.getTaskService();

//    List<Task> taskList = taskService.createTaskQuery().taskCandidateUser("accountancy").list();
//    List<Task> taskList = taskService.createTaskQuery().list();

    taskService.createTaskQuery().taskCandidateUser("accountancy")
        .list()
        .forEach(task -> {
          System.out.println("执行" + task.getName() + "任务...");
          taskService.complete(task.getId());
        });

    Thread.sleep(10000);

    taskService.createTaskQuery().taskCandidateUser("management")
        .list()
        .forEach(task -> {
          System.out.println("执行" + task.getName() + "任务...");
          taskService.complete(task.getId());
        });

//    taskList.forEach(task -> {
//      //认领任务
//      taskService.claim(task.getId(), "kermit");
//      taskService.createTaskQuery().taskAssignee("kermit").list().forEach(innerTask -> {
//        System.out.println(innerTask.getId() + ":" + innerTask.getName());
//
//        try {
//          Thread.sleep(3000);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//
//        taskService.complete(task.getId());
//      });
//
//    });
//    taskList.forEach(task -> System.out.println(task.getName()));

//    repositoryService.deleteDeployment(deployment.getId());
  }


}
