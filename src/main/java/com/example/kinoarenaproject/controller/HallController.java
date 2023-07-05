package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.AddHallDTO;
import com.example.kinoarenaproject.model.DTOs.HallDTO;
import com.example.kinoarenaproject.service.HallService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HallController extends AbstractController{
    @Autowired
    HallService hallService;


    @PostMapping("/admin/halls")
    public HallDTO add(@RequestBody AddHallDTO addData){

        HallDTO hall=hallService.add(addData);

        return hall;
    }

    @PostMapping("halls/by_cinema")
    public List<AddHallDTO> filter(@RequestBody int cinemaId){
        List<AddHallDTO>hallList=hallService.filterByCinema(cinemaId);
        return hallList;
    }


    @PutMapping("/admin/halls/{id}")
    public HallDTO edit(@RequestBody HallDTO editData, @PathVariable int id){

        HallDTO hall=hallService.edit(editData,id);
        return hall;
    }

    @GetMapping("/halls/{id}")
    public HallDTO getById(@PathVariable int id){
        return hallService.getById(id);
    }

    @DeleteMapping("/admin/halls/{id}")
    public HallDTO remove(@PathVariable int id){

        return  hallService.remove(id);
    }




}
