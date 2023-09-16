package com.assignment.smartaviation.config;

import com.assignment.smartaviation.model.FlightDetails;
import com.assignment.smartaviation.model.LuggageDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final static String FLIGHT_DETAILS_JSON="/data/flightDetails.json";
    private final static String LUGGAGE_DETAILS_JSON="/data/luggageDetails.json";

    List<FlightDetails> flightDetails;
    List<LuggageDetails> luggageDetails;

    //created constructor for initialization
    public DataLoader() throws IOException {
    }

    @Override
    public void run(String... args) throws Exception {
        TypeReference<List<FlightDetails>> flightTypeReference=new TypeReference<List<FlightDetails>>() {
        };
        TypeReference<List<LuggageDetails>> LuggageTypeReference=new TypeReference<List<LuggageDetails>>() {
        };
        InputStream flightInputStream= TypeReference.class.getResourceAsStream(FLIGHT_DETAILS_JSON);

        InputStream luggageInputStream= TypeReference.class.getResourceAsStream(LUGGAGE_DETAILS_JSON);

        ObjectMapper mapper = new ObjectMapper();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ssXXX");
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        LocalDateDeserializer dateTimeDeserializer = new LocalDateDeserializer(formatter);
        LocalDateSerializer dateSerializer = new LocalDateSerializer(formatter);

        javaTimeModule.addDeserializer(LocalDate.class, dateTimeDeserializer);
        javaTimeModule.addSerializer(LocalDate.class, dateSerializer);

        mapper.registerModule(javaTimeModule);

        flightDetails=mapper.readValue(flightInputStream,flightTypeReference);

        luggageDetails=mapper.readValue(luggageInputStream, LuggageTypeReference);

        if(flightInputStream !=null || luggageInputStream !=null){
            loadFlightDetailsData();
            loadLuggageDetailsData();
        }
    }

    public MultiKeyMap loadFlightDetailsData(){

        final MultiKeyMap multiKeyMap=new MultiKeyMap();

          flightDetails.forEach(item-> multiKeyMap.put(item.getFlightNumber(),item.getDepartureDate(),item));
          log.info("multi  "+multiKeyMap);

        return multiKeyMap;
    }

    public  Map<Integer, LuggageDetails> loadLuggageDetailsData(){
        Map<Integer, LuggageDetails> luggageDetailsMap = luggageDetails.stream()
                .collect(Collectors.toMap(LuggageDetails::getFlightId, Function.identity()));

        return luggageDetailsMap;
    }
}


