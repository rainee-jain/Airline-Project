package com.assignment.smartaviation.service;

import com.assignment.smartaviation.config.DataLoader;
import com.assignment.smartaviation.exception.AirlineServiceException;
import com.assignment.smartaviation.model.BaggageDetails;
import com.assignment.smartaviation.model.BaggageDetailsResponse;
import com.assignment.smartaviation.model.FlightDetails;
import com.assignment.smartaviation.model.LuggageDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

@Service
@Slf4j
public class AirlineAirportService {

    @Autowired
    private DataLoader dataLoader;

    public BaggageDetailsResponse getFlightDetails(String airportCode, String date) {
        ArrayList<Integer> flightIdDepartingList = new ArrayList<>();
        ArrayList<Integer> flightIdArrivingList = new ArrayList<>();

        int numberofFlightsDeparting=0;
        int numberofFLightArriving=0;

        int totalNumberOfArrivalBaggage = 0;
        int totalNumberOfDepartingBaggage = 0;
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
        Map<Integer, LuggageDetails> luggageDetailsMap = dataLoader.loadLuggageDetailsData();

            for (Object entry : flightDetailsMap.entrySet()) {
            Map.Entry<MultiKey<?>, Object> mapEntry = (Map.Entry<MultiKey<?>, Object>) entry;
            FlightDetails flightDetails = (FlightDetails) mapEntry.getValue();
            if (flightDetails.getDepartureAirportIATACode().equals(airportCode) &&
                    flightDetails.getDepartureDate().equals(inputDate)) {
                numberofFlightsDeparting++;
                flightIdDepartingList.add(flightDetails.getFlightId());
            }
            if (flightDetails.getArrivalAirportIATACode().equals(airportCode) &&
                    flightDetails.getDepartureDate().equals(inputDate)) {
                numberofFLightArriving++;
                flightIdArrivingList.add(flightDetails.getFlightId());
            }
        }
         totalNumberOfArrivalBaggage = getTotalNumberOfArrivalBaggage(flightIdArrivingList, luggageDetailsMap);
         totalNumberOfDepartingBaggage = getTotalNumberOfDepartingBaggage(flightIdDepartingList, luggageDetailsMap);
        return new BaggageDetailsResponse(numberofFLightArriving,numberofFlightsDeparting,    totalNumberOfArrivalBaggage,totalNumberOfDepartingBaggage);

    }

    private  int getTotalNumberOfArrivalBaggage(ArrayList<Integer> flightIdArrivingList, Map<Integer, LuggageDetails> luggageDetailsMap){
        int baggageNumberOfPeicesArriving=0;

        for(int flightIdArriving:flightIdArrivingList){
            if(luggageDetailsMap.get(flightIdArriving)!=null) {
                LuggageDetails luggageDetails = luggageDetailsMap.get(flightIdArriving);
                baggageNumberOfPeicesArriving += luggageDetails.getBaggage().stream().mapToInt(BaggageDetails::getPieces).sum();
            }
        }
        return baggageNumberOfPeicesArriving;
    }

    private int getTotalNumberOfDepartingBaggage(ArrayList<Integer> flightIdDepartingList, Map<Integer, LuggageDetails> luggageDetailsMap){
        int baggageNumberOfPeicesDeparting = 0;

        for(int flightIdDeparting:flightIdDepartingList){
            if(luggageDetailsMap.get(flightIdDeparting)!=null) {
                LuggageDetails luggageDetails = luggageDetailsMap.get(flightIdDeparting);
                baggageNumberOfPeicesDeparting += luggageDetails.getBaggage().stream().mapToInt(BaggageDetails::getPieces).sum();
            }
        }
        return baggageNumberOfPeicesDeparting;
    }
}
