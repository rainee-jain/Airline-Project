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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class AirlineAirportServiceTest {
    @Mock
    private DataLoader dataLoader;
    @InjectMocks
    private AirlineAirportService airlineAirportService;

    private MultiKeyMap flightDetailsMapMock=new MultiKeyMap();

    private Map<Integer, LuggageDetails> luggageDetailsMockMap=new HashMap<>();

    @Test
    public void shouldGetFlightDetailsWithAirportCodeAndDateTest(){

        String inputDate="2013-09-15";
        List<BaggageDetails> baggageDetailsList= Arrays.asList(new BaggageDetails(0,891,"lb",358),new BaggageDetails(1,482,"kg",539),new BaggageDetails(2,991,"lb",49));
        List<CargoDetails> cargoDetailsList= Arrays.asList(new CargoDetails(0,197,"lb",667),new CargoDetails(1,862,"kg",570),new CargoDetails(1,762,"lb",370));

        List<BaggageDetails> baggageDetailsList1= Arrays.asList(new BaggageDetails(0,891,"lb",30),new BaggageDetails(1,482,"kg",10),new BaggageDetails(2,991,"lb",5));
        List<CargoDetails> cargoDetailsList1= Arrays.asList(new CargoDetails(0,197,"lb",667),new CargoDetails(1,862,"kg",570),new CargoDetails(1,762,"lb",370));

        flightDetailsMapMock.put(8225, LocalDate.of(2013,9,15),new FlightDetails(0,8225,"LAX","GDN",LocalDate.of(2013,9,15)));
        flightDetailsMapMock.put(3392,LocalDate.of(1996,05,24),new FlightDetails(1,3392,"YYT","KRK",LocalDate.of(1996,05,24)));
        flightDetailsMapMock.put(3346, LocalDate.of(2013,9,15),new FlightDetails(2,3346,"LAX","GDN",LocalDate.of(2013,9,15)));
        flightDetailsMapMock.put(3456, LocalDate.of(2013,9,15),new FlightDetails(3,3456,"GDN","LAX",LocalDate.of(2013,9,15)));

        luggageDetailsMockMap.put(0,new LuggageDetails(0,baggageDetailsList,cargoDetailsList));
        luggageDetailsMockMap.put(2,new LuggageDetails(2,baggageDetailsList1,cargoDetailsList1));

        Mockito.when(dataLoader.loadFlightDetailsData()).thenReturn(flightDetailsMapMock);
        Mockito.when(dataLoader.loadLuggageDetailsData()).thenReturn(luggageDetailsMockMap);

        BaggageDetailsResponse baggageDetailsResponse=airlineAirportService.getFlightDetails("LAX",inputDate);

        Assertions.assertEquals(0,baggageDetailsResponse.getNumberOfBaggageArriving());
        Assertions.assertEquals(991,baggageDetailsResponse.getNumberOfBaggageDeparting());
        Assertions.assertEquals(1,baggageDetailsResponse.getNumberofFlightsArriving());
        Assertions.assertEquals(2,baggageDetailsResponse.getNumberofFlightsDeparting());

    }
    @Test
    public void shouldGetNoDataWhenDataNotResent(){

        String inputDate="2013-05-25";
        List<BaggageDetails> baggageDetailsList= Arrays.asList(new BaggageDetails(0,891,"lb",358),new BaggageDetails(1,482,"kg",539),new BaggageDetails(2,991,"lb",49));
        List<CargoDetails> cargoDetailsList= Arrays.asList(new CargoDetails(0,197,"lb",667),new CargoDetails(1,862,"kg",570),new CargoDetails(1,762,"lb",370));

        List<BaggageDetails> baggageDetailsList1= Arrays.asList(new BaggageDetails(0,891,"lb",30),new BaggageDetails(1,482,"kg",10),new BaggageDetails(2,991,"lb",5));
        List<CargoDetails> cargoDetailsList1= Arrays.asList(new CargoDetails(0,197,"lb",667),new CargoDetails(1,862,"kg",570),new CargoDetails(1,762,"lb",370));

        flightDetailsMapMock.put(8225, LocalDate.of(2013,9,15),new FlightDetails(0,8225,"LAX","GDN",LocalDate.of(2013,9,15)));
        flightDetailsMapMock.put(3392,LocalDate.of(1996,05,24),new FlightDetails(1,3392,"YYT","KRK",LocalDate.of(1996,05,24)));
        flightDetailsMapMock.put(3346, LocalDate.of(2013,9,15),new FlightDetails(2,3346,"LAX","GDN",LocalDate.of(2013,9,15)));
        flightDetailsMapMock.put(3456, LocalDate.of(2013,9,15),new FlightDetails(3,3456,"GDN","LAX",LocalDate.of(2013,9,15)));

        luggageDetailsMockMap.put(0,new LuggageDetails(0,baggageDetailsList,cargoDetailsList));
        luggageDetailsMockMap.put(2,new LuggageDetails(2,baggageDetailsList1,cargoDetailsList1));

        Mockito.when(dataLoader.loadFlightDetailsData()).thenReturn(flightDetailsMapMock);
        Mockito.when(dataLoader.loadLuggageDetailsData()).thenReturn(luggageDetailsMockMap);

        BaggageDetailsResponse baggageDetailsResponse=airlineAirportService.getFlightDetails("LAX",inputDate);

        Assertions.assertEquals(0,baggageDetailsResponse.getNumberOfBaggageArriving());
        Assertions.assertEquals(0,baggageDetailsResponse.getNumberOfBaggageDeparting());
        Assertions.assertEquals(0,baggageDetailsResponse.getNumberofFlightsArriving());
        Assertions.assertEquals(0,baggageDetailsResponse.getNumberofFlightsDeparting());
    }

    @Test
    public void shouldThrowExceptionWhenDateIsIncorrectFormat(){

        String inputDate="201305-25";
        Mockito.when(dataLoader.loadFlightDetailsData()).thenReturn(flightDetailsMapMock);
        Mockito.when(dataLoader.loadLuggageDetailsData()).thenReturn(luggageDetailsMockMap);

       Assertions.assertThrows(AirlineServiceException.class,()->
                              {airlineAirportService.getFlightDetails("LAX",inputDate);});
    }
}
