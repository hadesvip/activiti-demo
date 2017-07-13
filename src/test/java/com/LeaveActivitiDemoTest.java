package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * Created by wangyong on 2017/7/13.
 */
public class LeaveActivitiDemoTest {


  @Test
  public void leaveActivitiDemoTest() {
    ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration
        .createStandaloneProcessEngineConfiguration()
        .setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useSSL=false")
        .setJdbcUsername("root")
        .setJdbcPassword("root")
        .setJdbcDriver("com.mysql.jdbc.Driver")
        .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

    ProcessEngine processEngine = engineConfiguration.buildProcessEngine();

    //服务接口
    TaskService taskService = processEngine.getTaskService();
    HistoryService historyService = processEngine.getHistoryService();
    RuntimeService runtimeService = processEngine.getRuntimeService();
    RepositoryService repositoryService = processEngine.getRepositoryService();

    //发布流程
    Deployment deployment = repositoryService.createDeployment()
        .addClasspathResource("activiti/leaveActivitiDemo.bpmn20.xml").deploy();

    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
        .singleResult();
    assertEquals("leaveProcess", processDefinition.getKey());
    System.out.println(processDefinition.getKey());

    Map<String, Object> paramsMap = new HashMap<>();
    paramsMap.put("name", "张三");
    paramsMap.put("days", 3);

    //开启流程
    ProcessInstance processInstance = runtimeService
        .startProcessInstanceByKey("leaveProcess", paramsMap);

    assertNotNull(processDefinition);
    System.out.println(
        "processInstance's Id is:" + processInstance.getId() + ",processInstance 's definedId is:"
            + processInstance.getProcessDefinitionId());

    Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader")
        .singleResult();

    assertNotNull(deptLeaderTask);

    assertEquals("领导审批", deptLeaderTask.getName());

    //认领任务
    taskService.claim(deptLeaderTask.getId(), "leaderUser");

    paramsMap = new HashMap<>();
    paramsMap.put("approved", true);

    //完成任务
    taskService.complete(deptLeaderTask.getId(), paramsMap);

    //完成任务应该为空
    assertNull(taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult());

    //完成的任务数量
    long count = historyService.createHistoricProcessInstanceQuery().finished().count();
    assertEquals(1, count);
  }

}
