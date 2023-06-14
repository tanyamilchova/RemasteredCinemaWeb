package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Genre;
import com.example.kinoarenaproject.model.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findById(int id);

    List<Movie> findByGenre(Genre genre);
}
