package com.assignment.smartaviation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuggageDetailsResponse {
    private double totalCargoWeight;
    private double totalBaggageWeight;
    private double totalWeight;
}
