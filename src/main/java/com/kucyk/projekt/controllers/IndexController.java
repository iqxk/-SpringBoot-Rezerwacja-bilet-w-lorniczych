package com.kucyk.projekt.controllers;

import com.kucyk.projekt.controllers.filters.FlightRouteFilter;
import com.kucyk.projekt.models.FlightRoute;
import com.kucyk.projekt.models.User;
import com.kucyk.projekt.services.AirlineServiceImpl;
import com.kucyk.projekt.services.FlightRouteServiceImpl;
import com.kucyk.projekt.services.UserServiceImpl;
import com.kucyk.projekt.validators.CustomUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class IndexController
{
    @Autowired
    UserServiceImpl userService;

    @Autowired
    FlightRouteServiceImpl flightRouteService;

    @Autowired
    AirlineServiceImpl airlineService;

    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        //binder.addCustomFormatter(new AddressFormatter());
        binder.addValidators(new CustomUserValidator());
    }


    @GetMapping("/")
    public String index(Model m, Optional<FlightRouteFilter> flightRouteFilter)
    {
        if(flightRouteFilter.isPresent()) {
            if(flightRouteFilter.get().isClear()){
                m.addAttribute("filter", new FlightRouteFilter());
                m.addAttribute("flightRoutes", flightRouteService.getAllRoutes());
            }else{
                flightRouteFilter.get().prepareValues();
                m.addAttribute("flightRoutes", flightRouteService.findByFilter(flightRouteFilter.get()));
                m.addAttribute("filter", flightRouteFilter.get());
            }
        }

        m.addAttribute("airlines", airlineService.getAll());
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model m)
    {
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute("user") @Valid User u, BindingResult br, Model m)
    {
        if(br.hasErrors())
            return "register";
        if(userService.checkIfUserExists(u))
        {
            m.addAttribute("error_message", "Użytkownik o podanym loginie już istnieje!");
            return "register";
        }

        userService.saveUser(u);
        return "redirect:/";
    }
}
