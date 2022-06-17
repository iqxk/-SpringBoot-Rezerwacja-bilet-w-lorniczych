package com.kucyk.projekt.repositories;

import com.kucyk.projekt.controllers.filters.FlightRouteFilter;
import com.kucyk.projekt.models.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long>
{

    @Query("select fr from FlightRoute fr join fetch fr.airline a " +
            "where (lower(fr.start) like lower(:#{#filter.getPhraseDB()}) " +
            "or lower(fr.destination) like lower(:#{#filter.getPhraseDB()}))")
    List<FlightRoute> findByPhrase(@Param("filter") FlightRouteFilter flightRouteFilter);

    @Query("select fr from FlightRoute fr join fetch fr.airline a " +
            "where (lower(fr.start) like lower(:#{#filter.getPhraseDB()}) " +
            "or lower(fr.destination) like lower(:#{#filter.getPhraseDB()})) " +
            "and (lower(fr.airline.name) like lower(:#{#filter.getAirline().getName()}))")
    List<FlightRoute> findByPhraseAndAirline(@Param("filter") FlightRouteFilter flightRouteFilter);

    List<FlightRoute> findAllByAirlineId(Long id);
}
