package com.example.kinoarenaproject;
import com.example.kinoarenaproject.service.EmailSenderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableScheduling
@SpringBootApplication
public class KinoArenaProjectApplication {
    @Autowired
    private EmailSenderService senderCervice;

    public static void main(String[] args) {
        SpringApplication.run(KinoArenaProjectApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


/*    @EventListener(ApplicationReadyEvent.class)
    public void sendEmail(){
        senderCervice.sendEmail(
                "margot11margot11@gmail.com",
                "This is subject",
                "Hi! I'm Tanya.");
    }


 */


}
