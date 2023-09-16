package com.assignment.smartaviation.controller;

import com.assignment.smartaviation.exception.AirlineServiceException;
import com.assignment.smartaviation.model.BaggageDetailsResponse;
import com.assignment.smartaviation.model.LuggageDetailsResponse;
import com.assignment.smartaviation.service.AirlineAirportService;
import com.assignment.smartaviation.service.AirlineWeightService;
import com.assignment.smartaviation.validation.AirlineDataValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/V1")
@Slf4j
public class AirlineController {

    @Autowired
    private AirlineWeightService airlineWeightService;
    @Autowired
    private AirlineAirportService airlineAirportService;
    @Autowired
    private AirlineDataValidatorService airlineDataValidatorService;


    @GetMapping("/getWeightDetails")
    public ResponseEntity<LuggageDetailsResponse> requestedWeightDetails
            (@RequestParam(name="flightNumber", required = false)  int flightNumber,
             @RequestParam(name = "date", required = false) String date)
                      {

        log.info("Received request for flight number : {} and date: {}", flightNumber,date);

        airlineDataValidatorService.validateWeightDetailsData(flightNumber, date);

        LuggageDetailsResponse airlineServiceFlightDetails = airlineWeightService.getWeightDetails(flightNumber, date);
        if (airlineServiceFlightDetails == null){
            throw new AirlineServiceException.Builder()
                    .errorCode("DATA_NOT_FOUND")
                    .errorMessage("No data found for the provided input")
                    .status(404).build();

        } else {
            return new ResponseEntity<>(airlineServiceFlightDetails, HttpStatus.OK);
        }
    }

    @GetMapping("/getFlightDetails")
    public ResponseEntity<BaggageDetailsResponse> requestedFlightDetails
            (@RequestParam(name="airportCode", required = false) String airportCode,
             @RequestParam(name = "date", required = false) String date) {

        log.info("Received request for airport code : {} and date: {}", airportCode,date);

        airlineDataValidatorService.validateFlightDetailsData(airportCode, date);

        BaggageDetailsResponse airlineAirportServiceFlightDetails = airlineAirportService.getFlightDetails(airportCode, date);

        if (airlineAirportServiceFlightDetails == null){
            throw new AirlineServiceException.Builder()
                    .errorCode("DATA_NOT_FOUND")
                    .errorMessage("No data found for the provided input")
                    .status(404).build();

        } else {
            return new ResponseEntity<>(airlineAirportServiceFlightDetails, HttpStatus.OK);
        }
    }
}
