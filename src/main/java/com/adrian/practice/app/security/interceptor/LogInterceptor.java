package com.adrian.practice.app.security.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor to log request details such as URL, start time, end time, and execution time.
 * Implements HandlerInterceptor to provide pre- and post-processing of requests.
 * @author Adrian SA
 * @version 1.0
 * @since 2025-08
 */
@Component("logInterceptor")
public class LogInterceptor implements HandlerInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    /**
     * Logs the request URL and start time before the request is handled.
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * @param Object handler
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        logger.info("Request URL: {}", request.getRequestURL());
        return true;
    }

    /**
     * Logs the end time and execution time after the request has been handled.
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * @param Object handler
     * @param ModelAndView modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        

        // logger.info("End Time: {}", endTime);
        logger.info("Execute Time: {} ms", executeTime);

    }



}
