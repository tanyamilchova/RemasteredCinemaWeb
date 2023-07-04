package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.service.ProjectionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
public class ProjectionController extends AbstractController {
    @Autowired
    private ProjectionService projectionService;

    //-------------------------------------------------------------------------------------------------
    @PostMapping("projections")
    public ProjectionDTO add(@RequestBody AddProjectionDTO  addProjectionDTO,HttpSession session){
        int userId=loggedId(session);
        return projectionService.add(addProjectionDTO,userId);
    }
    @DeleteMapping("projections/{id}")
    public ProjectionDTO remove(@PathVariable int id, HttpSession session){
        int userId=loggedId(session);
        return projectionService.remove(id,userId);
    }
    @GetMapping("projections/{id}")
    public ProjectionDTO getProjectionById(@PathVariable int id){
        return projectionService.getById(id);
    }

    @PutMapping("projections/{id}")
    public ProjectionDTO edit(@RequestBody EditProjectionDTO editProjectionDTO,@PathVariable int id, HttpSession session){
        System.out.println("Controller");
        int userId=loggedId(session);
        return projectionService.edit(editProjectionDTO,id,userId);

    }
    @GetMapping("/projections/{id}/avSeats")
    public HashSet<List<Integer>> getAvSeats(@PathVariable int id){

        return projectionService.getAvSeats(id);
    }
    @PostMapping ("/projections/filter")
    public HashSet<ProjectionDTO> projectionsByCinemaAndMovie(@RequestBody ProjectionsFilterDTO projectionsFilterDTO){
        HashSet<ProjectionDTO>hashSet=projectionService.filterByCinemaAndMovie(projectionsFilterDTO.getCinemaId(),projectionsFilterDTO.getMovieId());
        return hashSet;
    }
    @GetMapping("projections/filter/{id}")
    public HashSet<ProjectionDTO> projectionByCinema(@PathVariable int id){

        HashSet<ProjectionDTO>projectionDTOS=projectionService.filterByCinema(id);
        return projectionDTOS;
    }
    @PostMapping ("/projections/filterDateTimeCinema")
    public HashSet<ProjectionDTO> projectionsByDateTimeAndCinema(@RequestBody ProjectionByDateTimeCinema projectionByDateTimeCinema){
        HashSet<ProjectionDTO>hashSet=projectionService.projectionsByDateTimeAndCinema(
                projectionByDateTimeCinema.getDate(),projectionByDateTimeCinema.getStartTime(),projectionByDateTimeCinema.getCinemaId());
        return hashSet;
    }


}
