package com.adrian.practice.app.security.interceptor;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to register custom interceptors in the Spring MVC application.
 * It implements WebMvcConfigurer to customize the default Spring MVC configuration.
 * The class registers a logging interceptor to log incoming requests and responses.
 * @author Adrian SA
 * @version 1.0
 * @since 2025-08
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

    @Qualifier("logInterceptor")
    private final HandlerInterceptor logInterceptor;

    /**
     * Constructor to inject the logging interceptor.
     * @param logInterceptor
     */
    public InterceptorConfig(HandlerInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    /**
     * Registers the logging interceptor to intercept all incoming requests.
     * @param registry
     */
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
            // .addPathPatterns("/**");
    }
}
