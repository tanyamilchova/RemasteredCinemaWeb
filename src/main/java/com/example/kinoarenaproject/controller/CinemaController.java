package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.service.CinemaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
public class CinemaController extends AbstractController{

    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/cinemas")
    public CinemaDTO add(@RequestBody AddCinemaDTO addData, HttpSession session){
        int id=loggedId(session);
        CinemaDTO cinema = cinemaService.add(addData, id);
        return cinema;
    }

   @PostMapping("cinemas/filter")
    public List<AddCinemaDTO> filter(@RequestBody int cityId){
        List<AddCinemaDTO>cinemaList=cinemaService.filterByCity(cityId);
        return cinemaList;
   }


   @PutMapping("/cinemas/{id}")
   public CinemaDTO edit(@RequestBody CinemaDTO editData,@PathVariable int id, HttpSession session){
        int userId=loggedId(session);
        return cinemaService.edit(editData,id,userId);

   }

   @GetMapping("/cinemas/{id}")
    public CinemaDTO getById(@PathVariable int id){
        return cinemaService.getById(id);
   }



   @GetMapping("/cinemas/all")
    public HashSet<CinemaDTO> getAll(){
        HashSet<CinemaDTO>cinemas=cinemaService.getAll();
        return cinemas;
   }

   @DeleteMapping("/cinemas/{id}")
   public CinemaDTO remove(@PathVariable int id, HttpSession session){
        int userId=loggedId(session);
       return  cinemaService.remove(id,userId);
   }


}



