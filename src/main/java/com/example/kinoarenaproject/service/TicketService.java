package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Util;
import com.example.kinoarenaproject.model.DTOs.TicketBookDTO;
import com.example.kinoarenaproject.model.DTOs.TicketInfoDTO;
import com.example.kinoarenaproject.model.entities.Projection;
import com.example.kinoarenaproject.model.entities.Ticket;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.ProjectionRepository;
import com.example.kinoarenaproject.model.repositories.TicketRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class TicketService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;


     public TicketInfoDTO buyTicket(TicketBookDTO ticketBuyDTO, int loggedId) {


        Projection projection=ifPresent(projectionRepository.findById(ticketBuyDTO.getProjectionId()));
        if(!Util.validSeat(ticketBuyDTO,projection)){
            throw new BadRequestException("Invalid seat");
        }
        int takenTiket=ticketRepository.countByProjectionIdAndRowNumberAndColNumber(ticketBuyDTO.getProjectionId(),ticketBuyDTO.getRowNumber(),ticketBuyDTO.getColNumber());
        if(takenTiket!=0){
            throw new BadRequestException("Resource is taken");
        }
         Ticket ticket=new Ticket();
         ticket.setUser(userById(loggedId));
         ticket.setProjection(projection);
         ticket.setRowNumber(ticketBuyDTO.getRowNumber());
         ticket.setColNumber(ticketBuyDTO.getColNumber());
         ticketRepository.save(ticket);

         ArrayList<Integer>rowCol=new ArrayList<>();
         rowCol.add(ticketBuyDTO.getRowNumber());
         rowCol.add(ticketBuyDTO.getColNumber());
         ticketBuyDTO.setSeat(rowCol);

        return mapper.map(ticket,TicketInfoDTO.class);
        }

    public TicketInfoDTO getTicketById(int id) {

        Ticket ticket=ifPresent(ticketRepository.findById(id));
        return mapper.map(ticket,TicketInfoDTO.class);
    }

    public HashSet<TicketInfoDTO> getAllTicketsOfUser(int id) {
        User user=userById(id);
        ArrayList<Ticket>list=new ArrayList<>();
        list.addAll(ticketRepository.findAllByUser_Id(id));
        HashSet<TicketInfoDTO>ticketHashSet=new HashSet<>();
        for (Ticket t:list){
           ticketHashSet.add( mapper.map(t,TicketInfoDTO.class));
        }
        return ticketHashSet;
    }
}