package com.assignment.smartaviation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuggageDetails {
    private int flightId;
    private List<BaggageDetails> baggage;
    private List<CargoDetails> cargo;
}
