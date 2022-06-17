package com.kucyk.projekt.services;

import com.kucyk.projekt.models.Airline;
import com.kucyk.projekt.models.FlightRoute;
import com.kucyk.projekt.models.Ticket;
import com.kucyk.projekt.repositories.AirlineRepository;
import com.kucyk.projekt.repositories.FlightRouteRepository;
import com.kucyk.projekt.repositories.TicketRepository;
import com.kucyk.projekt.services.interfaces.AirlineService;
import com.kucyk.projekt.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("airlineService")
public class AirlineServiceImpl implements AirlineService
{
    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    FlightRouteRepository flightRouteRepository;

    public Airline findById(Long id)
    {
        return airlineRepository.findById(id).get();
    }

    public List<Airline> getAll()
    {
        return airlineRepository.findAll();
    }

    public Airline getById(long id)
    {
        return airlineRepository.findById(id).get();
    }

    public List<Airline> getAllAirlines()
    {
        return airlineRepository.findAll();
    }

    public Airline saveAirline(Airline a)
    {
        return airlineRepository.save(a);
    }

    public boolean checkIfAirlineExistsById(Long id)
    {
        var airline = airlineRepository.findById(id);
        return airline != null;
    }

    public void deleteAirlineById(Long id)
    {
        List<FlightRoute> flightRoutes = flightRouteRepository.findAllByAirlineId(id);
        for(FlightRoute fr: flightRoutes)
        {
            List<Ticket> tickets = ticketRepository.findAllByFlightRouteId(fr.getId());
            for(Ticket t: tickets)
                ticketRepository.deleteById(t.getId());
            flightRouteRepository.deleteById(fr.getId());
        }
        airlineRepository.deleteById(id);
    }
}
