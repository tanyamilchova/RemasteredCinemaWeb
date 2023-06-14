package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.ValidationUtils;
import com.example.kinoarenaproject.model.DTOs.ProjectionAvailableSeatsDTO;
import com.example.kinoarenaproject.model.DTOs.TicketBookDTO;
import com.example.kinoarenaproject.model.DTOs.TicketInfoDTO;
import com.example.kinoarenaproject.model.entities.Movie;
import com.example.kinoarenaproject.model.entities.Projection;
import com.example.kinoarenaproject.model.entities.Ticket;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.ProjectionRepository;
import com.example.kinoarenaproject.model.repositories.TicketRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

//    public TicketInfoDTO book(TicketBookDTO bookTicket, int loggedId) {
//        Projection projection = checkOptionalIsPresent(projectionRepository.findById(bookTicket.getProjectionId()), "non-existent projection");
//        int countExistTicket = ticketRepository.countByProjectionIdAndRowNumberAndColNumber(projection.getId(), bookTicket.getRowNumber(), bookTicket.getColNumber());
//        if (countExistTicket != 0) {
//            throw new NotFoundException("Choose a free seat! This seat is reserved.");
//        }
//
//        Ticket ticket = new Ticket();
//        User u = userById(loggedId);
//        ticket.setUser(u);
//        ticket.setProjection(projection);
//        ticket.setRowNumber(bookTicket.getRowNumber());
//        ticket.setColNumber(bookTicket.getColNumber());
//        ticketRepository.save(ticket);
//        return mapper.map(ticket, TicketInfoDTO.class);
//    }

//    public TicketInfoDTO getTicketById(int id, int idUser) {
//        Ticket ticket = checkOptionalIsPresent(ticketRepository.findById(id), "non-existent ticket");
//        if (!admin(idUser) || ticket.getUser().getId() != idUser) {
//            throw new UnauthorizedException("Unauthorized role!");
//        }
//        return mapper.map(ticket, TicketInfoDTO.class);
//    }


    //------------------------------------------------------------
     public TicketInfoDTO buyTicket(TicketBookDTO ticketBuyDTO, int loggedId) {


        Projection projection=ifPresent(projectionRepository.findById(ticketBuyDTO.getProjectionId()));
        if(!ValidationUtils.validSeat(ticketBuyDTO,projection)){
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
//         mapper.map(ticketBuyDTO,Ticket.class);
         ticketRepository.save(ticket);


         ArrayList<Integer>rowCol=new ArrayList<>();
         rowCol.add(ticketBuyDTO.getRowNumber());
         rowCol.add(ticketBuyDTO.getColNumber());
         ticketBuyDTO.setSeat(rowCol);
         // направи мястото 1
//         pr.getPlan().add(rowCol);
//
//         projectionRepository.save();
         System.out.println(rowCol);
//         System.out.println(pr.getPlan());

        return mapper.map(ticket,TicketInfoDTO.class);
        }

    public TicketInfoDTO getTicketById(int id, int userId) {
        User user=userById(userId);
        if(!admin(userId)){
            throw new UnauthorizedException("Unauthorised");
        }
        Ticket ticket=ifPresent(ticketRepository.findById(id));
        return mapper.map(ticket,TicketInfoDTO.class);
    }

    public HashSet<TicketInfoDTO> getAllTicketsOfUser(int id) {
        User user=userById(id);
        ArrayList<Ticket>list=new ArrayList<>();
        list.addAll(ticketRepository.findAllByUser_Id(id));
//        HashSet<TicketInfoDTO>ticketHashSet=new HashSet<>();
        HashSet<TicketInfoDTO>ticketHashSet=new HashSet<>();
        for (Ticket t:list){
           ticketHashSet.add( mapper.map(t,TicketInfoDTO.class));
        }
        return ticketHashSet;
    }
}