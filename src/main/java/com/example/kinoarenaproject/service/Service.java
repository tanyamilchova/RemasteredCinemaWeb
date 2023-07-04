package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Util;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CityRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@org.springframework.stereotype.Service
public abstract class Service {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    public ModelMapper mapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public User userById(int id){
        Optional<User>opt=userRepository.findById(id);
        if(!opt.isPresent()){
            throw new NotFoundException("No user");
        }
        User u=opt.get();
        return u;
    }

    public boolean admin(int userId){
       User u=userById(userId);
      return u.getRole_name().equals(Util.ADMIN) ;
    }


    public <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    public <T> T ifPresent(Optional<T> opt){
        if(!opt.isPresent()){
            throw new NotFoundException("Resource not found");
        }
        return opt.get();
    }
}
