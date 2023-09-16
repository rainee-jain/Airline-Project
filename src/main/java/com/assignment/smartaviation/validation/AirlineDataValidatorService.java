package com.assignment.smartaviation.validation;

import com.assignment.smartaviation.exception.AirlineServiceException;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class AirlineDataValidatorService {

    public void validateWeightDetailsData(int flightNumber, String date) {
        if (flightNumber <= 0 ){
            throw new AirlineServiceException.Builder()
                    .errorCode("FLIGHT_NUMBER_INVALID")
                    .errorMessage("Flight number not valid in input")
                    .status(400)
                    .build();
        }
        validateDate(date);
    }

    public void validateFlightDetailsData(String airportCode, String date) {
        if (airportCode == null || airportCode.isEmpty()){
            throw new AirlineServiceException.Builder()
                    .errorCode("AIRPORT_CODE_MISSING")
                    .errorMessage("Airport code is missing in input")
                    .status(400)
                    .build();
        }

        validateDate(date);
    }


    private void validateDate(String date) {
        if (date == null){
            throw new AirlineServiceException.Builder()
                    .errorCode("DATE_MISSING")
                    .errorMessage("Date not provided in input")
                    .status(400)
                    .build();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFormat.parse(date);
        } catch (ParseException exception){
            exception.printStackTrace();
            throw new AirlineServiceException.Builder()
                    .errorCode("DATE_INVALID")
                    .errorMessage("Date is not valid in input")
                    .status(400)
                    .build();
        }
    }
}
