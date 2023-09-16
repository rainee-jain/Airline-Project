About this Project
This is a simple Spring Boot Implementation which immplements two functionalities:

1. For requested Flight Number and date will respond with following :
       a. Cargo Weight for requested Flight
       b. Baggage Weight for requested Flight
       c. Total Weight for requested Flight
2. For requested IATA Airport Code and date will respond with following :
       a. Number of flights departing from this airport,
       b. Number of flights arriving to this airport,
       c. Total number (pieces) of baggage arriving to this airport,
       d. Total number (pieces) of baggage departing from this airport.

API Endpoints
This Application is originally tested on tomcat server. There are two api's with respect to its functionalities.
First API takes input flightNumber and date and returns Weight Information 

endpoint- http://localhost:8080/api/V1/getWeightDetails/?flightNumber={}&&date={}

Second API takes input airportCode and date and returns Flight Information

endpoint- http://localhost:{port-number}/api/V1/getFlightDetails?airportCode={}&&date={}

You can access API through http://localhost:{port-number}/userApplication/v1/getCountryInfo

To test the application with your own data load the data in resource folder in json files.

To Build the Application run command: mvn clean install
