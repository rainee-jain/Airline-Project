package com.assignment.smartaviation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDetails {
    private int id;
    private double weight;
    private String weightUnit;
    private int pieces;
}

