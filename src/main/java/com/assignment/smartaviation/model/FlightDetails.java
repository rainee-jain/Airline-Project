package com.assignment.smartaviation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDetails {
    int flightId;
    int flightNumber;
    String departureAirportIATACode;
    String arrivalAirportIATACode;
    LocalDate departureDate;
}
