package com.kucyk.projekt.config;

import com.kucyk.projekt.models.*;
import com.kucyk.projekt.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class RepositoriesInitializer
{
    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LuggageRepository luggageRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FlightRouteRepository flightRouteRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileRepository fileRepository;

    @Bean
    InitializingBean init() {
        return() ->
        {
            if(roleRepository.findAll().isEmpty())
            {
                roleRepository.save(new Role(Role.Types.ROLE_USER));
                roleRepository.save(new Role(Role.Types.ROLE_ADMIN));
            }

            if(userRepository.findAll().isEmpty())
            {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                Role roleUser = roleRepository.findById(1L).get();
                Role roleAdmin = roleRepository.findById(2L).get();

                User user = new User("user");
                user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                user.setPassword(passwordEncoder.encode("user"));

                User admin = new User("admin");
                admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                admin.setPassword(passwordEncoder.encode("admin"));

                userRepository.save(user);
                userRepository.save(admin);
            }

            if(airlineRepository.findAll().isEmpty())
            {
                airlineRepository.save(new Airline("LOT"));
                airlineRepository.save(new Airline("KLM"));
                airlineRepository.save(new Airline("Norwegian Air Shuttle"));
            }

            if(flightRouteRepository.findAll().isEmpty())
            {
                flightRouteRepository.save(new FlightRoute("Warszawa", "Berlin", airlineRepository.getById(1L), LocalTime.of(1, 30), LocalTime.of(12, 0), 99.99));
                flightRouteRepository.save(new FlightRoute("Warszawa", "Moskwa", airlineRepository.getById(2L), LocalTime.of(1, 15), LocalTime.of(14, 40), 79.99));
                flightRouteRepository.save(new FlightRoute("Wrocław", "Warszawa", airlineRepository.getById(3L), LocalTime.of(0, 20), LocalTime.of(15, 0), 29.99));
            }

            if(statusRepository.findAll().isEmpty())
            {
                statusRepository.save(new Status("Oczekiwanie na zapłatę"));
                statusRepository.save(new Status("Anulowano"));
                statusRepository.save(new Status("Opłacono"));
            }
        };
    }
}
