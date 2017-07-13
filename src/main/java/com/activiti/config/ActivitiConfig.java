package com.activiti.config;

import javax.sql.DataSource;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * activiti工作流配置
 * Created by wangyong on 2017/7/8.
 */
@Configuration
public class ActivitiConfig {


  @Bean
  public PlatformTransactionManager jpaTransactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource,
      PlatformTransactionManager transactionManager) {

    SpringProcessEngineConfiguration engineConfiguration = new SpringProcessEngineConfiguration();
    engineConfiguration.setDataSource(dataSource);
    engineConfiguration.setDatabaseType("mysql");
    engineConfiguration.setDatabaseSchemaUpdate("true");
    engineConfiguration.setJobExecutorActivate(false);
    engineConfiguration.setAsyncExecutorEnabled(true);

    engineConfiguration.setTransactionManager(transactionManager);

    engineConfiguration
        .setDeploymentResources(new Resource[]{new ClassPathResource("activiti/hello.bpmn20.xml")});
    return engineConfiguration;
  }


  @Bean
  public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration configuration) {
    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(configuration);
    return processEngineFactoryBean;
  }

  @Bean
  public RepositoryService repositoryService(ProcessEngineFactoryBean processEngine)
      throws Exception {
    return processEngine.getObject().getRepositoryService();
  }

  @Bean
  public RuntimeService runtimeService(ProcessEngineFactoryBean processEngine) throws Exception {
    return processEngine.getObject().getRuntimeService();
  }

  @Bean
  public HistoryService historyService(ProcessEngineFactoryBean processEngine) throws Exception {
    return processEngine.getObject().getHistoryService();
  }

  @Bean
  public TaskService taskService(ProcessEngineFactoryBean processEngine) throws Exception {
    return processEngine.getObject().getTaskService();
  }


}
