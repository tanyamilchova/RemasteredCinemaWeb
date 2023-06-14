package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.ValidationUtils;
import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CityRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    CityRepository cityRepository;

    public UserWithoutPasswordDTO login(LoginDTO loginData) {

        Optional<User> opt = userRepository.findByEmail(loginData.getEmail());
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        if (!userRepository.existsByEmail(loginData.getEmail())) {
            throw new UnauthorizedException("Wrong credentials");
        }
        User u = opt.get();
        if (! u.isEnable()) {

            throw new UnauthorizedException("Wrong credentials");
        }
        if(passwordEncoder.matches(loginData.getPassword(), u.getPassword())){
            System.out.println();
            return mapper.map(u, UserWithoutPasswordDTO.class);
        }
        else {
            throw new UnauthorizedException("Wrong credentials");
            }

    }


    public UserWithoutPasswordDTO register(RegisterDTO registerData) {
        if (!registerData.getPassword().equals((registerData).getConfirmPassword())) {
            throw new BadRequestException("Password mismatched");
        }
        if(ValidationUtils.validRegisterData(registerData)){
            if (userRepository.existsByEmail(registerData.getEmail())) {
                throw new BadRequestException("Email already exist");
            }
        } else {
            throw new BadRequestException("Inadequate input for password");
        }
        User u = mapper.map(registerData, User.class);
        u.setDateTimeRegistration(LocalDateTime.now());
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setConfirmatronToken(generateConfirmationToken());

        userRepository.save(u);

        sendConfirmationEmail(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);

    }

    private String generateConfirmationToken(){
        return UUID.randomUUID().toString();
    }
    private void sendConfirmationEmail(User user){
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Confirm your email");
        message.setText("To confirm your email, please click the link below:\n\n" +
                "http://localhost:8080/confirm?token=" + user.getConfirmatronToken());
        new Thread(()->  mailSender.send(message)).start();

    }
    public boolean confirmEmail(String token){
        User user=userRepository.findAllByConfirmatronToken(token).orElseThrow(()->new NotFoundException("Token not found"));
        user.setConfirmatronToken(null);
        user.setEnable(true);
        userRepository.save(user);
        return true;
    }

    public UserWithoutPasswordDTO getById(int id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return mapper.map(opt.get(), UserWithoutPasswordDTO.class);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public UserWithoutPasswordDTO changePassword(ChangePassDTO changePassData, int id) {
        if(! ValidationUtils.isValidPassword(changePassData.getNewPassword())) {
            throw new UnauthorizedException("Week password");
        }
        Optional<User> opt = userRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        User u = opt.get();

            u.setPassword(passwordEncoder.encode(changePassData.getNewPassword()));
            userRepository.save(u);

            return mapper.map(u, UserWithoutPasswordDTO.class);
        }

    public UserWithoutPasswordDTO editProfile(EditProfileDTO editProfileData, int id) {

        if(!ValidationUtils.validEditProfilData(editProfileData)){
            throw new BadRequestException("Wrong input data");
        }
        if (userRepository.existsByEmail(editProfileData.getEmail()) &&
                userRepository.findByEmail(editProfileData.getEmail()).get().getId()!=id) {
            throw new UnauthorizedException("Email already exist");
        }

        if(! ValidationUtils.validEditData(editProfileData)){
            throw new UnauthorizedException("Input data not valid");
        }
        Optional<User> opt = userRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }

        User u = opt.get();
        u.setPhone_number(editProfileData.getPhone_number());
        u.setEmail(editProfileData.getEmail());
        u.setBirth_date(editProfileData.getBirth_date());
        u.setFirst_name(editProfileData.getFirst_name());
        u.setLast_name(editProfileData.getLast_name());
        u.setGender(editProfileData.getGender());
        u.setCity_id(editProfileData.getCity_id());

        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO delete(int adminId, int userId) {
            User user=ifPresent(userRepository.findById(adminId));
            Optional <User>opt=userRepository.findById(userId);
            if(!opt.isPresent()){
                throw new NotFoundException("User not found");
            }
            User userToDelete=opt.get();
            userRepository.delete(userToDelete);
            return   mapper.map(userToDelete,UserWithoutPasswordDTO.class);

        }

    @Scheduled(fixedRate = 1000*60*5)
    public void deleteUnverifiedUsers() {

        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);
        List<User> unverifiedUsers = userRepository.findAllByEnableFalseAAndDateTimeRegistration(cutoffTime);
        userRepository.deleteAll(unverifiedUsers);
    }



}








