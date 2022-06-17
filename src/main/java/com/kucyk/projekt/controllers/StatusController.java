package com.kucyk.projekt.controllers;

import com.kucyk.projekt.services.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/status")
public class StatusController
{
    @Autowired
    StatusServiceImpl statusService;


}
