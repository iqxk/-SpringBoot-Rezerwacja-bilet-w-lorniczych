package com.kucyk.projekt.controllers;

import com.kucyk.projekt.models.Customer;
import com.kucyk.projekt.models.Luggage;
import com.kucyk.projekt.models.Ticket;
import com.kucyk.projekt.services.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController
{
    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    LuggageServiceImpl luggageService;

    @Autowired
    FlightRouteServiceImpl flightRouteService;

    @Autowired
    StatusServiceImpl statusService;

    @GetMapping("/add")
    public String addTicket(Model m, @ModelAttribute("ticket") Ticket t)
    {
        m.addAttribute("ticket", t);
        return "/ticket/add";
    }

    @PostMapping("/add")
    public String processTicket(@ModelAttribute("ticket") @Valid Ticket t, BindingResult br, Model m, RedirectAttributes ra)
    {
        if(br.hasErrors())
            return "/ticket/add";

        Luggage l = luggageService.saveLuggage(new Luggage(t.getLuggage().getWeight()));
        t.setCustomer((Customer) Hibernate.unproxy(customerService.getById(t.getCustomer().getId())));
        t.setFlightRoute(flightRouteService.getById(t.getFlightRoute().getId()));
        t.setLuggage(l);
        t.setStatus(statusService.getById(t.getStatus().getId()));
        t.setPrice(t.getFlightRoute().getPrice()+(t.getLuggage().getWeight()*4));

        ra.addFlashAttribute("ticket", t);
        return "redirect:/ticket/confirm";
    }

    @GetMapping("/confirm")
    public String confirmTicket(@ModelAttribute("ticket") @Valid Ticket t, BindingResult br, Model m, RedirectAttributes ra)
    {
        t.setCustomer((Customer) Hibernate.unproxy(customerService.getById(t.getCustomer().getId())));
        t.setFlightRoute(flightRouteService.getById(t.getFlightRoute().getId()));
        t.setLuggage((Luggage) Hibernate.unproxy(luggageService.getById(t.getLuggage().getId())));
        t.setStatus(statusService.getById(t.getStatus().getId()));
        m.addAttribute("ticket", t);
        return "/ticket/confirm";
    }

    @PostMapping("/confirm")
    public String processConfirmTicket(@ModelAttribute("ticket") @Valid Ticket t, BindingResult br, Model m, RedirectAttributes ra)
    {
        t.setCustomer((Customer) Hibernate.unproxy(customerService.getById(t.getCustomer().getId())));
        t.setFlightRoute(flightRouteService.getById(t.getFlightRoute().getId()));
        t.setLuggage((Luggage) Hibernate.unproxy(luggageService.getById(t.getLuggage().getId())));
        t.setStatus(statusService.getById(t.getStatus().getId()));
        ticketService.saveTicket(t);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String showTicketsList(Model m, Principal principal)
    {
        m.addAttribute("tickets", ticketService.getAllTicketsByUsername(principal.getName()));
        return "/ticket/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/list")
    public String showAllTicketsList(Model m)
    {
        m.addAttribute("tickets", ticketService.getAllTickets());
        return "/ticket/list";
    }

    @GetMapping("/select")
    public String selectTicket(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(ticketService.checkIfTicketExistsById(id.get()))
            {
                Ticket t = ticketService.getById(id.get());
                t.setCustomer((Customer) Hibernate.unproxy(customerService.getById(t.getCustomer().getId())));
                t.setFlightRoute(flightRouteService.getById(t.getFlightRoute().getId()));
                t.setLuggage((Luggage) Hibernate.unproxy(luggageService.getById(t.getLuggage().getId())));
                t.setStatus(statusService.getById(t.getStatus().getId()));
                m.addAttribute("ticket", t);
                return "/ticket/details";
            }
            else
            {
                return "redirect:/";
            }
        }
        else
        {
            return "redirect:/";
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/edit")
    public String editTicket(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(ticketService.checkIfTicketExistsById(id.get()))
            {
                Ticket t = ticketService.getById(id.get());
                t.setCustomer((Customer) Hibernate.unproxy(customerService.getById(t.getCustomer().getId())));
                t.setFlightRoute(flightRouteService.getById(t.getFlightRoute().getId()));
                t.setLuggage((Luggage) Hibernate.unproxy(luggageService.getById(t.getLuggage().getId())));
                t.setStatus(statusService.getById(t.getStatus().getId()));
                m.addAttribute("ticket", t);
                m.addAttribute("statuses", statusService.getAllStatuses());
                return "/ticket/edit";
            }
            else
            {
                return "redirect:/";
            }
        }
        else
        {
            return "redirect:/";
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/edit")
    public String processEditTicket(@ModelAttribute("ticket") @Valid Ticket t, BindingResult br, Model m, RedirectAttributes ra)
    {
        if(br.hasErrors())
            return "/ticket/edit";

        ticketService.saveTicket(t);
        return "redirect:/ticket/admin/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete")
    public String deleteTicket(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(ticketService.checkIfTicketExistsById(id.get()))
            {
                ticketService.deleteTicketById(id.get());
                return "redirect:/ticket/admin/list";
            }
            else
            {
                return "redirect:/";
            }
        }
        else
        {
            return "redirect:/";
        }
    }
}
