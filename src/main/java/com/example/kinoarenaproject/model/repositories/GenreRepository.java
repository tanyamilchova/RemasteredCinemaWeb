package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
