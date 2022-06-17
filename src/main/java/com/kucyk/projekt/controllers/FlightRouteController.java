package com.kucyk.projekt.controllers;

import com.kucyk.projekt.models.*;
import com.kucyk.projekt.services.*;
import com.kucyk.projekt.services.interfaces.LuggageService;
import org.apache.logging.log4j.Level;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/flight_route")
public class FlightRouteController
{
    @Autowired
    FlightRouteServiceImpl flightRouteService;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    LuggageServiceImpl luggageService;

    @Autowired
    StatusServiceImpl statusService;

    @Autowired
    AirlineServiceImpl airlineService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/add")
    public String addFlightRoute(Model m, @ModelAttribute("flightRoute") FlightRoute fr)
    {
        fr.setAirline(airlineService.getById(1L));
        m.addAttribute("flightRoute", fr);
        m.addAttribute("airlines", airlineService.getAll());
        return "/flight_route/add";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public String processFlightRoute(@ModelAttribute("flightRoute") @Valid FlightRoute fr, BindingResult br, Model m, RedirectAttributes ra)
    {
        if(br.hasErrors())
            return "/flight_route/add";

        fr.setAirline(airlineService.getById(fr.getAirline().getId()));
        flightRouteService.saveFlightRoute(fr);

        return "redirect:/";
    }

    @GetMapping("/select")
    public String selectFlightRoute(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(customerService.checkIfCustomerExistsByUsername(principal.getName()))
            {
                var t = new Ticket();
                t.setCustomer(customerService.getByUsername(principal.getName()));
                t.setFlightRoute(flightRouteService.getById(id.get()));
                t.setLuggage(new Luggage());
                t.setStatus(statusService.getById(1L));
                ra.addFlashAttribute("ticket", t);
                return "redirect:/ticket/add";
            }
            else
            {
                ra.addAttribute("id", id.get());
                return "redirect:/customer/add";
            }
        }
        else
        {
            return "redirect:/";
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/edit")
    public String editFlightRoute(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(flightRouteService.checkIfFlightRouteExistsById(id.get()))
            {
                FlightRoute fr = flightRouteService.getById(id.get());
                fr.setAirline(airlineService.findById(fr.getAirline().getId()));
                m.addAttribute("flightRoute", fr);
                m.addAttribute("airlines", airlineService.getAll());
                return "/flight_route/edit";
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
    public String processEditFlightRoute(@ModelAttribute("flightRoute") @Valid FlightRoute fr, BindingResult br, Model m, RedirectAttributes ra)
    {
        if(br.hasErrors())
            return "/flight_route/edit";

        flightRouteService.saveFlightRoute(fr);
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete")
    public String deleteFlightRoute(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(flightRouteService.checkIfFlightRouteExistsById(id.get()))
            {
                flightRouteService.deleteFlightRouteById(id.get());
                return "redirect:/";
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
