package com.kucyk.projekt.services;

import com.kucyk.projekt.models.Customer;
import com.kucyk.projekt.models.Ticket;
import com.kucyk.projekt.models.User;
import com.kucyk.projekt.repositories.CustomerRepository;
import com.kucyk.projekt.repositories.TicketRepository;
import com.kucyk.projekt.repositories.UserRepository;
import com.kucyk.projekt.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ticketService")
public class TicketServiceImpl implements TicketService
{
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Ticket getCustomerById(Long customerId)
    {
        return ticketRepository.findByCustomerId(customerId);
    }

    public void saveTicket(Ticket t)
    {
        ticketRepository.save(t);
    }

    public List<Ticket> getAllTickets()
    {
        return ticketRepository.findAll();
    }

    public boolean checkIfTicketExistsById(Long id)
    {
        var user = ticketRepository.findById(id);
        return user != null;
    }

    public Ticket getById(Long id)
    {
        return ticketRepository.findById(id).get();
    }

    public List<Ticket> getAllTicketsByUsername(String username)
    {
        return ticketRepository.findAllByCustomerId(customerRepository.findByUserId(userRepository.findByUsername(username).getId()).getId());
    }

    public void deleteTicketById(Long id)
    {
        ticketRepository.delete(ticketRepository.findById(id).get());
    }
}
