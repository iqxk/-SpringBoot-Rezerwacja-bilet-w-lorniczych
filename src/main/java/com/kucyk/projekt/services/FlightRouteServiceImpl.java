package com.kucyk.projekt.services;

import com.kucyk.projekt.controllers.filters.FlightRouteFilter;
import com.kucyk.projekt.models.FlightRoute;
import com.kucyk.projekt.models.Ticket;
import com.kucyk.projekt.repositories.FlightRouteRepository;
import com.kucyk.projekt.repositories.TicketRepository;
import com.kucyk.projekt.services.interfaces.FlightRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("routeService")
public class FlightRouteServiceImpl implements FlightRouteService
{
    @Autowired
    FlightRouteRepository flightRouteRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<FlightRoute> getAllRoutes()
    {
        return flightRouteRepository.findAll();
    }

    public FlightRoute getById(Long id)
    {
        return flightRouteRepository.findById(id).get();
    }

    public boolean checkIfFlightRouteExistsById(Long id)
    {
        var flightRoute = flightRouteRepository.findById(id);
        return flightRoute != null;
    }

    public FlightRoute saveFlightRoute(FlightRoute fr)
    {
        return flightRouteRepository.save(fr);
    }

    public void deleteFlightRouteById(Long id)
    {
        List<Ticket> tickets = ticketRepository.findAllByFlightRouteId(id);
        for(Ticket t: tickets)
            ticketRepository.deleteById(t.getId());
        flightRouteRepository.deleteById(id);
    }

    public List<FlightRoute> findByFilter(FlightRouteFilter flightRouteFilter)
    {
        flightRouteFilter.prepareValues();
        if(flightRouteFilter.isAirlineNull())
            return flightRouteRepository.findByPhrase(flightRouteFilter);
        else
            return flightRouteRepository.findByPhraseAndAirline(flightRouteFilter);
    }
}
