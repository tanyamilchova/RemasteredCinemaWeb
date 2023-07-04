package com.example.kinoarenaproject.Interseptor;

import com.example.kinoarenaproject.controller.Util;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Component
public class MyInterseptor implements HandlerInterceptor, Ordered {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getSession().getAttribute(Util.LOGGED_ID)==null){
            String requestURI=request.getRequestURI();
            if(! ( requestURI.endsWith("/users/login") || requestURI.endsWith("/users/register") || requestURI.contains("/confirm")) ){
                throw new UnauthorizedException("You have to login first");
            }
        }
        request.setAttribute("startTime",System.currentTimeMillis());
        return true;



    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    long time=(long) request.getAttribute("startTime");
    long timeTaken=System.currentTimeMillis()-time;
        System.out.println("The time taken for processing the request is: "+timeTaken +" milliSec");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
