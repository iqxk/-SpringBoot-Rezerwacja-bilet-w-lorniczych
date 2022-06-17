package com.kucyk.projekt.controllers;

import com.kucyk.projekt.models.Customer;
import com.kucyk.projekt.models.User;
import com.kucyk.projekt.services.CustomerServiceImpl;
import com.kucyk.projekt.services.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/add")
    public String addCustomer(Model m, RedirectAttributes ra, Principal principal, @RequestParam(value="id") Optional<Long> id)
    {
        if(id.isPresent())
        {
            if(!customerService.checkIfCustomerExistsByUsername(principal.getName()))
            {
                var c = new Customer();
                c.setUser(userService.findUserByUsername(principal.getName()));
                m.addAttribute("customer", c);
                return "/customer/add";
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

    @PostMapping("/add")
    public String processCustomer(@ModelAttribute("customer") @Valid Customer c, BindingResult br, Model m, RedirectAttributes ra)
    {
        //System.out.println(c.getId() + " " + c.getUser().toString());
        if(br.hasErrors())
            return "/customer/add";

        customerService.saveCustomer(c);

        ra.addAttribute("id", c.getId());
        return "redirect:/";
    }
}
