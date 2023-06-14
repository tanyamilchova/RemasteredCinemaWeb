package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.service.TicketService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
public class TicketController extends AbstractController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets/{id}")
    public TicketInfoDTO getById(@PathVariable int id, HttpSession session) {
        int idUser = loggedId(session);
        return ticketService.getTicketById(id, idUser);
    }

//    @PostMapping("/tickets")
//    public TicketInfoDTO book(@RequestBody @Valid TicketBookDTO dto, HttpSession session) {
//        return ticketService.book(dto, loggedId(session));
//    }
    @PostMapping("/tickets")
    public TicketInfoDTO buyTicket(@RequestBody TicketBookDTO ticketBuyDTO,HttpSession session){
        return ticketService.buyTicket(ticketBuyDTO, loggedId(session));
    }
    @GetMapping("tickets")
    public HashSet<TicketInfoDTO> getAll(HttpSession session){

       int id=loggedId(session);
       HashSet<TicketInfoDTO>hashSet=ticketService.getAllTicketsOfUser(id);
        return hashSet;
    }

}