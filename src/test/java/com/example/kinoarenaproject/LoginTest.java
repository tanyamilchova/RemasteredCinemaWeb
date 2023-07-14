package com.example.kinoarenaproject;
import com.example.kinoarenaproject.controller.Util;
import com.example.kinoarenaproject.model.DTOs.LoginDTO;
import com.example.kinoarenaproject.model.DTOs.RegisterDTO;
import com.example.kinoarenaproject.model.DTOs.UserWithoutPasswordDTO;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import com.example.kinoarenaproject.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LoginTest {
    @Mock
    UserRepository userRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSuccessfulLogin() {
        LoginDTO loginData = new LoginDTO(Util.EMAIL, Util.PASSWORD);

        User user = new User();
        user.setEmail(Util.EMAIL);
        user.setPassword(Util.PASSWORD);
        user.setEnable(true);


        UserWithoutPasswordDTO expected=new UserWithoutPasswordDTO(user.getId(),Util.FIRST_NAME,Util.LAST_NAME,Util.EMAIL,Util.BIRTH_DATE,Util.CITY_ID,Util.GENDER,Util.PHONE,Util.ROLE_NAME,Util.PROFILE_IMAGE);
        when(userRepository.findByEmail(loginData.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginData.getPassword(), user.getPassword())).thenReturn(true);
        when(userService.login(loginData)).thenReturn(expected);
        UserWithoutPasswordDTO userWithoutPasswordDTO=userService.login(loginData);

        assertNotNull(userWithoutPasswordDTO);
        assertEquals(userWithoutPasswordDTO,expected);

    }

    @Test
    public void testWrongEmail() {
        LoginDTO loginData = new LoginDTO(Util.WRONG_EMAIL,Util.PASSWORD);

        when(userRepository.findByEmail(loginData.getEmail())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.login(loginData);
        });
    }
    @Test
    public void testWrongPassword() {
        LoginDTO loginData = new LoginDTO(Util.EMAIL, Util.WRONG_PASSWORD);

        User user = new User();
        user.setEmail(Util.EMAIL);
        user.setPassword(passwordEncoder.encode(Util.PASSWORD));
        user.setEnable(true);

        when(userRepository.findByEmail(loginData.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginData.getPassword(), user.getPassword())).thenReturn(false);

        Assertions.assertThrows(UnauthorizedException.class, () -> {
            userService.login(loginData);
        });
    }
    @Test
    public void testLoginUserNotEnabled() {
        LoginDTO loginDTO = new LoginDTO(Util.EMAIL, Util.PASSWORD);

        User user = new User();
        user.setEmail(Util.EMAIL);
        user.setPassword(Util.PASSWORD);
        user.setEnable(false);

        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));

        assertThrows( UnauthorizedException.class,()->{
            userService.login(loginDTO);});
    }
//--------------------------------------------------------------------------------------------------------------------
    @Test
    public void wrongConfirmPassword(){
        RegisterDTO registerDTO=new RegisterDTO(Util.FIRST_NAME,Util.LAST_NAME,Util.EMAIL,Util.PASSWORD,Util.CONFIRM_PASSWORD,Util.BIRTH_DATE,Util.CITY_ID,Util.GENDER,Util.PHONE,Util.ROLE_NAME,"error");
        User user=new User();
        user.setPassword(Util.PASSWORD);
        user.setEmail(Util.EMAIL);

        assertThrows(BadRequestException.class,()->userService.register(registerDTO));
        
    }
    @Test
    public void emailExist(){
        RegisterDTO registerDTO=new RegisterDTO(Util.FIRST_NAME,Util.LAST_NAME,Util.EMAIL,Util.PASSWORD,Util.CONFIRM_PASSWORD,Util.BIRTH_DATE,Util.CITY_ID,Util.GENDER,Util.PHONE,Util.ROLE_NAME,"error");
        User u=new User();
        u.setEmail(Util.EMAIL);
        assertThrows(BadRequestException.class,()->userService.register(registerDTO));
    }
    @Test
    public void invalidEmail(){
         String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

         RegisterDTO registerDTO=new RegisterDTO();
        registerDTO.setEmail(Util.WRONG_EMAIL);

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(registerDTO.getEmail());
        assertFalse(pattern.matcher(registerDTO.getEmail()).matches());
    }

    @Test
    public void invalidPhoneNumber(){
        RegisterDTO registerDTO=new RegisterDTO();
        registerDTO.setPhone_number(Util.INVALID_PHONE);


        String PHONE_REGEX = "^(\\+\\d{1,2})?\\s*(\\d{10})$";
        Pattern pattern= Pattern.compile(PHONE_REGEX);
        Matcher matcher=pattern.matcher(registerDTO.getPhone_number());
        assertFalse(pattern.matcher(registerDTO.getPhone_number()).matches());
    }
    @Test
    public void invalidPassword(){
        RegisterDTO registerDTO=new RegisterDTO();
        registerDTO.setPassword(Util.PASSWORD);
        String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern=Pattern.compile(PASSWORD_REGEX);
        Matcher matcher=pattern.matcher(registerDTO.getPassword());
        assertFalse(pattern.matcher(registerDTO.getPassword()).matches());
    }
//--------------------------------------------------------------------------------------------------------------------
}








