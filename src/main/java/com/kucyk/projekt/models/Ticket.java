package com.kucyk.projekt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="flight_route")
    private FlightRoute flightRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="luggage", nullable = false)
    private Luggage luggage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status", nullable = false)
    private Status status;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy") //yyyy-MM-dd
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @NumberFormat(pattern = "#.00")
    @Column(name = "price")
    private double price;
}
