package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.service.MovieService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
public class MovieController extends AbstractController {
    @Autowired
    private MovieService movieService;



    @PostMapping("/admin/movies")
    public MovieInfoDTO addMovie(@RequestBody @Valid AddMovieDTO addMovieDTO){
        return  movieService.addMovie(addMovieDTO);
    }
    @PutMapping("/admin/movies/{id}")
    public MovieInfoDTO editMovie(@RequestBody EditMovieDTO editMovieDTO,@PathVariable int id){
        return movieService.editMovie(editMovieDTO,id);
    }
    @GetMapping("/movies/{id}")
    public MovieInfoDTO getMovieById( @PathVariable int id){
        return movieService.getMovieById(id);
    }

    @GetMapping("/movies/all")
    public Page<MovieInfoDTO> getAllMovies(@RequestParam (defaultValue = "0")int page,
                                           @RequestParam (defaultValue = "10")int size,HttpSession session){
        int userId=loggedId(session);
        return movieService.getAllMovies(userId,page,size);
    }
    @DeleteMapping("/admin/movies/{id}")
    public MovieInfoDTO removeMovie(@PathVariable int id,HttpSession session){
        int userId=loggedId(session);
        return movieService.removeMovie(id,userId);
    }

    @PostMapping("/movies/date")
    public MovieWithProjectionListDTO filterByCinemaDate(@RequestBody MovieFilterDateDTO movieFilterDateDTO){
        return movieService.filterByDate(movieFilterDateDTO);
    }
}
