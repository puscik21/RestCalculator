package com.silvair.demo.controller;

import com.silvair.demo.service.history.HistoryService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HistoryRecordInterceptor implements HandlerInterceptor {

    private final HistoryService historyService;

    public HistoryRecordInterceptor(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        historyService.saveHistoryRecord(request.getRequestURI(), response.getStatus());
    }
}