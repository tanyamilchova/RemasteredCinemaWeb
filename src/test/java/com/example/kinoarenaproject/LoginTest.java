package com.example.kinoarenaproject;
import com.example.kinoarenaproject.model.DTOs.LoginDTO;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import com.example.kinoarenaproject.service.UserService;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LoginTest {
    @Mock
    UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    public void testSuccessfulLogin() {
        LoginDTO loginData = new LoginDTO("ivoivoivanov@abv.bg", "123456Sbb#");

        User user = new User();
        user.setEmail("ivoivoivanov@abv.bg");
        user.setPassword(("123456Sbb#"));
        user.setEnable(true);

       when(userRepository.findByEmail(loginData.getEmail())).thenReturn(Optional.of(user));
       lenient().when(passwordEncoder.matches(loginData.getPassword(), user.getPassword())).thenReturn(true);

       assertThrows( UnauthorizedException.class,()->{
            userService.login(loginData);});

    }

    @Test
    public void testWrongEmail() {
        LoginDTO loginData = new LoginDTO("ivoivoivanov@abv.bg", "123456Sbb#");

        when(userRepository.findByEmail(loginData.getEmail())).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> {
            userService.login(loginData);
        });
    }
    @Test
    public void testWrongPassword() {
        LoginDTO loginData = new LoginDTO("ivoivoivanov@abv.bg", "23456Sbb#");

        User user = new User();
        user.setEmail("ivoivoivanov@abv.bg");
        user.setPassword(passwordEncoder.encode("23456Sbb#"));
        user.setEnable(true);

        when(userRepository.findByEmail(loginData.getEmail())).thenReturn(Optional.of(user));
        lenient().when(passwordEncoder.matches(loginData.getPassword(), user.getPassword())).thenReturn(false);

        Assertions.assertThrows(UnauthorizedException.class, () -> {
            userService.login(loginData);
        });
    }
    @Test
    public void testLoginUserNotEnabled() {
        LoginDTO loginDTO = new LoginDTO("ivoivoivanov@abv.bg", "123456Sbb#");

        User user = new User();
        user.setEmail("ivoivoivanov@abv.bg");
        user.setPassword("123456Sbb#");
        user.setEnable(false);

        when(userRepository.existsByEmail(loginDTO.getEmail())).thenReturn(true);
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));

        assertThrows( UnauthorizedException.class,()->{
            userService.login(loginDTO);});
    }

}
