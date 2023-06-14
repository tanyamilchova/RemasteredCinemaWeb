package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Projection;
import com.example.kinoarenaproject.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
//    int countByProjectionIdAndRowNumberAndColNumber(int projectionId, int rowNumber, int colNumber);
    int countByProjectionIdAndRowNumberAndColNumber(int projId,int rowNum, int colNum);

    Optional<Ticket> findById(int id);

    List<Ticket>findAllByUser_Id(int id);
    HashSet<Ticket>findAllByProjection(Projection projection);

}
