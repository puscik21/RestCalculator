package com.silvair.demo.controller;

import com.silvair.demo.service.HistoryService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHistoryInterceptor implements HandlerInterceptor {

    private final HistoryService historyService;

    public RequestHistoryInterceptor(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        historyService.saveRequestHistory(request.getRequestURI(), response.getStatus());
    }
}