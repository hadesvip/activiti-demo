package com.activiti;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.LongFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * activiti 主要入口Process Engine
 * Created by wangyong on 2017/7/7.
 */
public class OnboardingRequest {

  public static void main(String[] args) {

    //流程引擎配置
    ProcessEngineConfiguration configuration = new StandaloneInMemProcessEngineConfiguration();
    configuration
        .setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useSSL=false")
        .setJdbcUsername("root")
        .setJdbcPassword("root")
        .setJdbcDriver("com.mysql.jdbc.Driver")
        .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

    //构建流程引擎
    ProcessEngine processEngine = configuration.buildProcessEngine();
    String peName = processEngine.getName();
    String peVersion = processEngine.VERSION;
    System.out.println("ProcessEngine [" + peName + "] Version: [" + peVersion + "]");

    //提供流程定义和部署仓库访问的服务
    RepositoryService repositoryService = processEngine.getRepositoryService();

    //发布自定义的activiti流程
    Deployment deployment = repositoryService.createDeployment()
        .addClasspathResource("activiti/onboarding.bpmn20.xml").deploy();

    //获取流程定义
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
        .deploymentId(deployment.getId()).singleResult();
    System.out.println("Found process definition ["
        + processDefinition.getName() + "] with id ["
        + processDefinition.getId() + "]");

    //流程实例
    RuntimeService runtimeService = processEngine.getRuntimeService();
    final ProcessInstance[] processInstance = {
        runtimeService.startProcessInstanceByKey("onboarding")};
    System.out.println("Onboarding process started with process instance id ["
        + processInstance[0].getProcessInstanceId()
        + "] key [" + processInstance[0].getProcessDefinitionKey() + "]");

    TaskService taskService = processEngine.getTaskService();
    FormService formService = processEngine.getFormService();
    HistoryService historyService = processEngine.getHistoryService();

    Scanner scanner = new Scanner(System.in);
    while (processInstance[0] != null && !processInstance[0].isEnded()) {
      List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("managers").list();
      System.out.println("Active outstanding tasks: [" + taskList.size() + "]");
      taskList.forEach(task -> {
        System.out.println("Processing Task [" + task.getName() + "]");

        //存储Form表单中的参数数据
        Map<String, Object> variableMap = Maps.newHashMap();
        TaskFormData formData = formService.getTaskFormData(task.getId());
        formData.getFormProperties().forEach(formProperty -> {
          if (StringFormType.class.isInstance(formProperty.getType())) {
            System.out.println(formProperty.getName() + "?");
            String value = scanner.nextLine();
            variableMap.put(formProperty.getId(), value);
          } else if (LongFormType.class.isInstance(formProperty.getType())) {
            System.out.println(formProperty.getName() + "? (Must be a whole number)");
            Long value = Long.valueOf(scanner.nextLine());
            variableMap.put(formProperty.getId(), value);
          } else if (DateFormType.class.isInstance(formProperty.getType())) {
            System.out.println(formProperty.getName() + "? (Must be a date m/d/yy)");
            variableMap.put(formProperty.getId(),
                DateTime.parse(scanner.nextLine(), DateTimeFormat.forPattern("m/d/yy")).toDate());
          } else {
            System.out.println("<form type not supported>");
          }
        });
        //录入数据任务完成
        taskService.complete(task.getId(), variableMap);

        final HistoricActivityInstance[] endActivity = {null};
        //历史活动列表
        List<HistoricActivityInstance> historicActivityList = historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(processInstance[0].getId()).finished()
            .orderByHistoricActivityInstanceEndTime().asc().list();
        historicActivityList.forEach(activity -> {
          //开始事件
          if (activity.getActivityType() == "startEvent") {
            System.out.println("BEGIN " + processDefinition.getName()
                + " [" + processInstance[0].getProcessDefinitionKey()
                + "] " + activity.getStartTime());
          }
          //结束事件,缓存下来,用于处理最后逻辑
          if (activity.getActivityType() == "endEvent") {
            endActivity[0] = activity;
          } else {
            System.out.println("-- " + activity.getActivityName()
                + " [" + activity.getActivityId() + "] "
                + activity.getDurationInMillis() + " ms");
          }
        });

        if (endActivity[0] != null) {
          System.out.println("-- " + endActivity[0].getActivityName()
              + " [" + endActivity[0].getActivityId() + "] "
              + endActivity[0].getDurationInMillis() + " ms");
          System.out.println("COMPLETE " + processDefinition.getName() + " ["
              + processInstance[0].getProcessDefinitionKey() + "] "
              + endActivity[0].getEndTime());
        }

        //重新查询下流程实例，确保最新的状态可用
        processInstance[0] = runtimeService.createProcessInstanceQuery()
            .processInstanceId(processInstance[0].getId())
            .singleResult();
      });
    }
    scanner.close();
  }

}
