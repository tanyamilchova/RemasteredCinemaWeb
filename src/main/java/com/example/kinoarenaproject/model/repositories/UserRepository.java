package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     boolean existsByEmail(String email);
     Optional<User>findByEmail(String email);
     Optional<User>findAllByConfirmatronToken(String token);



     @Query(value = "SELECT * FROM users   WHERE enable = false AND date_time_registration <= date_time_registration",nativeQuery = true)
     List<User> findAllByEnableFalseAAndDateTimeRegistration(@Param("cutoffTime")LocalDateTime cutoffTime);


}
