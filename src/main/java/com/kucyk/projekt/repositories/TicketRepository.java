package com.kucyk.projekt.repositories;

import com.kucyk.projekt.models.Airline;
import com.kucyk.projekt.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>
{

    Ticket findByCustomerId(Long customerId);

    List<Ticket> findAllByCustomerId(Long id);

    List<Ticket> findAllByFlightRouteId(Long id);
}
