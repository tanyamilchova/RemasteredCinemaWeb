package com.example.kinoarenaproject.model.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "users")
@Table
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column
     private  String first_name;
    @Column
    private String last_name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private LocalDate birth_date;
    @Column
    private int city_id;
    @Column
    private String gender;
    @Column
    private String phone_number;
    @Column
    private String role_name;
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    @Column(name = "confirmatron_token")
    private String confirmatronToken;
    @Column(name = "enable")
    private boolean enable;
    @Column(name = "date_time_registration")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime dateTimeRegistration;

    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,orphanRemoval = true)
    private List<Ticket>ticketList=new ArrayList<>();


}
