package com.assignment.smartaviation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaggageDetails {

        private int id;
        private double weight;
        private String weightUnit;
        private int pieces;
}
