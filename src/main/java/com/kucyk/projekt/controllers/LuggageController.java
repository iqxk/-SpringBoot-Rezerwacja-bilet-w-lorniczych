package com.kucyk.projekt.controllers;

import com.kucyk.projekt.services.LuggageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/luggage")
public class LuggageController
{
    @Autowired
    LuggageServiceImpl luggageService;


}
