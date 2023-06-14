package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.ErrorDTO;

import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDateTime;

public abstract class AbstractController {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e){
        return generateErrorDTO(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e){
        return generateErrorDTO(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e){
        return generateErrorDTO(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Exception e){
        e.printStackTrace();
        return generateErrorDTO(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO generateErrorDTO(Exception e, HttpStatus s){
        return ErrorDTO.builder()
                .msg(e.getMessage())
                .time(LocalDateTime.now())
                .status(s.value())
                .build();
    }

    protected  int loggedId(HttpSession session){
//        if(session.getAttribute(Constants.LOGGED_ID)==null){
//            throw new UnauthorizedException("You have to login first");
//        }
        return (int) session.getAttribute(Constants.LOGGED_ID);
    }
    protected boolean validSession(HttpSession session){
        if(session.getAttribute(Constants.LOGGED)==null){
            throw new UnauthorizedException("Invalid session. Login first.");
        }
        return true;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Invalid data");
        return generateErrorDTO(new BadRequestException(errorMessage), HttpStatus.BAD_REQUEST);
    }

}
