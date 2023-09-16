package com.assignment.smartaviation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaggageDetailsResponse {
    private int numberofFlightsArriving;
    private int numberofFlightsDeparting;
    private int numberOfBaggageArriving;
    private int numberOfBaggageDeparting;
}
