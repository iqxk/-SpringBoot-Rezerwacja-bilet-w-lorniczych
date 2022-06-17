package com.kucyk.projekt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "luggages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Luggage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NumberFormat(pattern = "#.00")
    @Column(name = "weight")
    private double weight;

    public Luggage(double weight)
    {
        this.weight = weight;
    }
}
