package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.*;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    ProjectionRepository projectionRepository;
    @Autowired
    private ModelMapper mapper;



    public MovieInfoDTO addMovie( AddMovieDTO addMovieDTO) {

//        User u=ifPresent(userRepository.findById(userId));
        Movie movie=mapper.map(addMovieDTO,Movie.class);
        movieRepository.save(movie);
        return mapper.map(movie,MovieInfoDTO.class);
    }

    public MovieInfoDTO editMovie(EditMovieDTO editMovieDTO,int id ) {

        Category category=ifPresent(categoryRepository.findById(editMovieDTO.getCategory()));
        Genre genre=ifPresent(genreRepository.findById(editMovieDTO.getGenre()));
        Movie movie=ifPresent(movieRepository.findById(id));
        movie.setTitle(editMovieDTO.getTitle());
        movie.setDescription(editMovieDTO.getDescription());
        movie.setDuration(editMovieDTO.getDuration());
        movie.setPremiere(editMovieDTO.getPremiere());
        movie.setDirector(editMovieDTO.getDirector());
        movie.setCast(editMovieDTO.getCast());
        movie.setCategory(category);
        movie.setDirector(editMovieDTO.getDirector());
        movie.setCast(editMovieDTO.getCast());
        movie.setGenre(genre);

        movieRepository.save(movie);
        return mapper.map(movie,MovieInfoDTO.class);




    }

    public MovieInfoDTO getMovieById(int id) {
        Movie movie=ifPresent(movieRepository.findById(id));
        return mapper.map(movie,MovieInfoDTO.class);
    }

    public Page<MovieInfoDTO> getAllMovies(int userId, int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        return movieRepository.findAll(pageable)
                .map(movie -> mapper.map(movie,MovieInfoDTO.class));
    }

    public MovieInfoDTO removeMovie(int id, int userId) {
        //ADMIN
        Movie movie=ifPresent(movieRepository.findById(id));
        movieRepository.delete(movie);
        return mapper.map(movie,MovieInfoDTO.class);
    }

    public MovieWithProjectionListDTO filterByDate(MovieFilterDateDTO movieFilterDateDTO) {
        Movie movie=ifPresent(movieRepository.findById(movieFilterDateDTO.getId()));
        Cinema cinema=ifPresent(cinemaRepository.findById(movieFilterDateDTO.getCinemaId()));
        List<Projection>projections =projectionRepository.findAllByCinemaDateMovie(cinema.getId(),movieFilterDateDTO.getDate(),movie.getId());

        MovieWithProjectionListDTO movieWithProjectionListDTO=mapper.map(movie,MovieWithProjectionListDTO.class);
        List<ProjectionDTO>list=new ArrayList<>();
        for (Projection p:projections) {
            list.add(mapper.map(p, ProjectionDTO.class));
        }
        movieWithProjectionListDTO.setProjectionDTOS(list);
        return movieWithProjectionListDTO;

    }
}