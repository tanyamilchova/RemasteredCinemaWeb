package com.example.kinoarenaproject.Interseptor;

import com.example.kinoarenaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@ComponentScan
public class WebConfig implements WebMvcConfigurer {
@Autowired
private AdminInterseptor adminInterseptor;
    @Autowired
    private MyInterseptor myInterseptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterseptor());

        registry.addInterceptor(adminInterseptor);
    }
}
