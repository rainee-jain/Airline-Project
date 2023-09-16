package com.assignment.smartaviation.service;

import com.assignment.smartaviation.config.DataLoader;
import com.assignment.smartaviation.exception.AirlineServiceException;
import com.assignment.smartaviation.model.*;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class AirlineWeightServiceTest {

    @Mock
    private DataLoader dataLoader;
    @InjectMocks
    private AirlineWeightService airlineWeightService;

    MultiKeyMap flightDetailsMapMock=new MultiKeyMap();
    Map<Integer, LuggageDetails> luggageDetailsMockMap=new HashMap<>();

    @Test
    public void shouldGetFlightDetailsWithFlightNumberTest() throws ParseException {

        String inputDate="2013-09-15";
        List<BaggageDetails> baggageDetailsList=Arrays.asList(new BaggageDetails(0,891,"lb",358),new BaggageDetails(1,482,"kg",539),new BaggageDetails(2,991,"lb",49));

        List<CargoDetails> cargoDetailsList= Arrays.asList(new CargoDetails(0,197,"lb",667),new CargoDetails(1,862,"kg",570),new CargoDetails(1,762,"lb",370));

       flightDetailsMapMock.put(8225,LocalDate.of(2013,9,15),new FlightDetails(0,8225,"LAX","GDN",LocalDate.of(2013,9,15)));
       luggageDetailsMockMap.put(0,new LuggageDetails(0,baggageDetailsList,cargoDetailsList));

       Mockito.when(dataLoader.loadFlightDetailsData()).thenReturn(flightDetailsMapMock);
       Mockito.when(dataLoader.loadLuggageDetailsData()).thenReturn(luggageDetailsMockMap);

       LuggageDetailsResponse luggageDetailsResponse = airlineWeightService.getWeightDetails(8225,inputDate);
       Assertions.assertEquals(2622.45,luggageDetailsResponse.getTotalWeight());
    }
    @Test
    public void shouldThrowExceptionWhenNoDataFoundTest() throws ParseException {

        String inputDate="2023-05-25";
        List<BaggageDetails> baggageDetailsList=Arrays.asList(new BaggageDetails(0,891,"lb",358),new BaggageDetails(1,482,"kg",539),new BaggageDetails(2,991,"lb",49));

        List<CargoDetails> cargoDetailsList= Arrays.asList(new CargoDetails(0,197,"lb",667),new CargoDetails(1,862,"kg",570),new CargoDetails(1,762,"lb",370));

        flightDetailsMapMock.put(8225,LocalDate.of(2013,9,15),new FlightDetails(0,8225,"LAX","GDN",LocalDate.of(2013,9,15)));
        luggageDetailsMockMap.put(0,new LuggageDetails(0,baggageDetailsList,cargoDetailsList));

        Mockito.when(dataLoader.loadFlightDetailsData()).thenReturn(flightDetailsMapMock);
        Mockito.when(dataLoader.loadLuggageDetailsData()).thenReturn(luggageDetailsMockMap);

        Assertions.assertThrows(AirlineServiceException.class,()->
                              {airlineWeightService.getWeightDetails(8225,inputDate);});
    }

    @Test
    public void shouldThrowExceptionWhenDateIsIncorrectFormat(){

        String inputDate="201305-25";
        Mockito.when(dataLoader.loadFlightDetailsData()).thenReturn(flightDetailsMapMock);
        Mockito.when(dataLoader.loadLuggageDetailsData()).thenReturn(luggageDetailsMockMap);

        Assertions.assertThrows(AirlineServiceException.class,()->
        {airlineWeightService.getWeightDetails(8225,inputDate);});
    }
}
