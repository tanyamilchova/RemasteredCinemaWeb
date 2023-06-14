package com.example.kinoarenaproject.Interseptor;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import com.example.kinoarenaproject.service.Service;
import com.example.kinoarenaproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.hibernate.service.spi.InjectService;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component

public class AdminInterseptor implements HandlerInterceptor, Ordered {




@Autowired

UserService userService;
@Autowired

UserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = null;
        String requestURI = request.getRequestURI();
        if(request.getSession().getAttribute(Constants.LOGGED_ID)!=null){
            System.out.println("1st");
            int id = (int) request.getSession().getAttribute(Constants.LOGGED_ID);
//            Assert.notNull(userService);
//            Assert.notNull(userRepository);
            user = userService.userById(id);
            if ( ( (user.getRole_name().equals(Constants.USER))) && (requestURI.endsWith("/delete") ) )  {
                System.out.println("Secoond Inters");
                throw new UnauthorizedException("You have to be ADMIN");
            }

            request.setAttribute("startTime", System.currentTimeMillis());
        }
            return true;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
