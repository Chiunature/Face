package com.example.intelligentcommunity.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Value("${upload.face}")
    String face;
    @Value("${upload.excel}")
    String excel;

    @Override
    //通过数据库的url去访问本机数据
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/IdeaPorject/IntelligentCommunity/upload/face/**").addResourceLocations("file:"+face);
        registry.addResourceHandler("/IdeaPorject/IntelligentCommunity/upload/excel/**").addResourceLocations("file:"+excel);
    }
}
