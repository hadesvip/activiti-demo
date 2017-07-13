package com.activiti.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wangyong on 2017/7/10.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.activiti", useDefaultFilters = false, includeFilters = {
    @ComponentScan.Filter(RestController.class), @ComponentScan.Filter(Controller.class)})
public class MVConfig extends WebMvcConfigurerAdapter {


  private FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4() {
    FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4 = new FastJsonHttpMessageConverter4();
    fastJsonHttpMessageConverter4.setSupportedMediaTypes(
        Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));

    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
        SerializerFeature.WriteMapNullValue);
    fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig);

    return fastJsonHttpMessageConverter4;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(fastJsonHttpMessageConverter4());

    super.configureMessageConverters(converters);
  }
}
