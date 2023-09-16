package com.assignment.smartaviation.service;

import com.assignment.smartaviation.config.DataLoader;
import com.assignment.smartaviation.exception.AirlineServiceException;
import com.assignment.smartaviation.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class AirlineWeightService {

    @Autowired
    private DataLoader dataLoader;

    public LuggageDetailsResponse getWeightDetails(int flightNumber, String date) {

        LocalDate inputDate;
        try{
            inputDate = LocalDate.parse(date);
        }catch(DateTimeParseException e){
            e.printStackTrace();
            log.error("Exception occurred while parsing datetime" + e.getMessage());
            log.error("Error occurred while parsing " + e.getParsedString() + e.getErrorIndex());
            throw new AirlineServiceException.Builder()
                    .errorCode("TECHNICAL_ERROR")
                    .errorMessage("Technical occurred while processing the request")
                    .status(400).build();
        }

        MultiKeyMap flightDetailsMap= dataLoader.loadFlightDetailsData();

        log.info("Flight Details Map"+flightDetailsMap);

        if(flightDetailsMap.containsKey(flightNumber,inputDate)){

            FlightDetails flightDetails = (FlightDetails) flightDetailsMap.get(flightNumber, inputDate);
            log.info("Flight Detail "+flightDetails);
            System.out.println("Luggage Response "+getLuggageDetailsResponse(flightDetails));
            return getLuggageDetailsResponse(flightDetails);
        }
        else throw new AirlineServiceException.Builder()
                .errorCode("DATA_NOT_FOUND")
                .errorMessage("No data found for the provided input")
                .status(404).build();

    }


    private LuggageDetailsResponse getLuggageDetailsResponse(FlightDetails flightDetails)
    {
        LuggageDetails luggageDetails= dataLoader.loadLuggageDetailsData().get(flightDetails.getFlightId());
        Double totalCargoWeight = getTotalCargoWeight(luggageDetails.getCargo());
        Double totalBaggageWeight = getTotalBaggageWeight(luggageDetails.getBaggage());
        Double totalLuggageWeight=totalBaggageWeight+totalCargoWeight;

        return new LuggageDetailsResponse(totalCargoWeight,totalBaggageWeight,totalLuggageWeight);
    }

    private Double getTotalCargoWeight(List<CargoDetails> cargoDetailsList) {
        AtomicReference<Double> totalWeight = new AtomicReference<>();
        totalWeight.set((Double) 0.0);
        if(!cargoDetailsList.isEmpty()) {
            cargoDetailsList.stream()
                    .forEach(cargoWeightDetails -> {
                        if (cargoWeightDetails.getWeightUnit().equals("lb")) {
                            totalWeight.set(totalWeight.get() + (cargoWeightDetails.getWeight() * 0.45));
                        } else {
                            totalWeight.set(totalWeight.get() + (cargoWeightDetails.getWeight()));
                        }
                    });
        }
        return totalWeight.get();
        //    else throw exception

    }
    private Double getTotalBaggageWeight(List<BaggageDetails> baggageDetailsList){
        AtomicReference<Double> totalWeight = new AtomicReference<>();
        totalWeight.set((Double) 0.0);
        if(!baggageDetailsList.isEmpty()) {

            baggageDetailsList.stream()
                    .forEach(baggageWeightDetails -> {
                        if (baggageWeightDetails.getWeightUnit().equals("lb")) {
                            totalWeight.set(totalWeight.get() + (baggageWeightDetails.getWeight() * 0.45));
                        } else {
                            totalWeight.set(totalWeight.get() + (baggageWeightDetails.getWeight()));
                        }
                    });
        }
        return totalWeight.get();

    }
}



