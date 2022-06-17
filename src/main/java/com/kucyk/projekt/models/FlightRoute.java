package com.kucyk.projekt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Optional;

@Entity
@Table(name = "flight_routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRoute
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 15)
    @Column(name = "start")
    private String start;

    @NotBlank
    @Size(min = 3, max = 15)
    @Column(name = "destination")
    private String destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="airline", nullable = false)
    private Airline airline;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "flight_time", nullable = false)
    private LocalTime flightTime;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @NumberFormat(pattern = "#.00")
    @Column(name = "price")
    private double price;

    public FlightRoute(String start, String destination, Airline airline, LocalTime flightTime, LocalTime startTime, double price)
    {
        this.start = start;
        this.destination = destination;
        this.airline = airline;
        this.flightTime = flightTime;
        this.startTime = startTime;
        this.price = price;
    }
}
