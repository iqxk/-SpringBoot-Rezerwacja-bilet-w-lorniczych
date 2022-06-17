package com.kucyk.projekt.controllers;

import com.kucyk.projekt.models.Airline;
import com.kucyk.projekt.models.FlightRoute;
import com.kucyk.projekt.services.AirlineServiceImpl;
import com.kucyk.projekt.services.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/airline")
public class AirlineController
{
    @Autowired
    AirlineServiceImpl airlineService;

    @Autowired
    FileServiceImpl fileService;

    @GetMapping("/admin/list")
    public String index(Model m)
    {
        List<Airline> airlinesList = airlineService.getAllAirlines();
        m.addAttribute("airlines", airlinesList);
        return "/airline/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/add")
    public String addAirline(Model m, @ModelAttribute("airline") Airline a)
    {
        m.addAttribute("airline", a);
        return "/airline/add";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public String processAirline(@ModelAttribute("airline") @Valid Airline a, BindingResult br, MultipartFile multipartFile, Model m, RedirectAttributes ra) throws IOException
    {
        if(br.hasErrors())
            return "/airline/add";
        var newA = airlineService.saveAirline(a);
        if(!multipartFile.isEmpty()) {
            fileService.saveFile(multipartFile, Long.toString(newA.getId()));
        }

        return "redirect:/airline/admin/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/edit")
    public String editAirline(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(airlineService.checkIfAirlineExistsById(id.get()))
            {
                Airline a = airlineService.getById(id.get());
                m.addAttribute("airline", a);
                return "/airline/edit";
            }
            else
            {
                return "redirect:/airline/admin/list";
            }
        }
        else
        {
            return "redirect:/airline/admin/list";
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/edit")
    public String processEditAirline(@ModelAttribute("airline") @Valid Airline a, BindingResult br, Model m, RedirectAttributes ra)
    {
        if(br.hasErrors())
            return "/airline/edit";

        airlineService.saveAirline(a);
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete")
    public String deleteAirline(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(airlineService.checkIfAirlineExistsById(id.get()))
            {
                airlineService.deleteAirlineById(id.get());
                return "redirect:/airline/admin/list";
            }
            else
            {
                return "redirect:/airline/admin/list";
            }
        }
        else
        {
            return "redirect:/airline/admin/list";
        }
    }
}
