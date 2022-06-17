package com.kucyk.projekt.repositories;

import com.kucyk.projekt.models.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long>
{

}
