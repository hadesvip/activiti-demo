package com.activiti.config;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by wangyong on 2017/7/10.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:app.properties")
public class DataSourceConfig {

  @Autowired
  private Environment env;


  @Bean("activitiDS")
  public DataSource dataSource() {
    DruidDataSource druidDS = new DruidDataSource();

    druidDS.setDriverClassName(env.getProperty("activiti.jdbcDriver"));
    druidDS.setUrl(env.getProperty("activiti.jdbc.url"));
    druidDS.setUsername(env.getProperty("activiti.jdbcUsername"));
    druidDS.setPassword(env.getProperty("activiti.jdbcPassword"));

    return druidDS;
  }


}
