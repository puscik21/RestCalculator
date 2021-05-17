package com.silvair.demo;

import com.silvair.demo.controller.RequestHistoryInterceptor;
import com.silvair.demo.service.HistoryService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final HistoryService historyService;

    public AppConfig(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHistoryInterceptor(historyService)).addPathPatterns("/**").excludePathPatterns("/calculate");
    }
}