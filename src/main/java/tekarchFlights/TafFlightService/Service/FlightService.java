package tekarchFlights.TafFlightService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tekarchFlights.TafFlightService.DTO.FlightRequest;
import tekarchFlights.TafFlightService.DTO.FlightResponse;

import java.util.Arrays;
import java.util.List;

@Service
public class FlightService {

    @Value("${datasource.service.url}")
    private String DATASTORE_BASE_URL;


   // private static final String DATASTORE_BASE_URL = "http://localhost:8081/api/flights";

    @Autowired
    private RestTemplate restTemplate;

    public List<?> searchFlights(String origin, String destination, String date) {
        String url = String.format("%s?origin=%s&destination=%s&date=%s", DATASTORE_BASE_URL, origin, destination, date);
        return restTemplate.getForObject(url, List.class);
    }

    public FlightResponse getFlightById(Long flightId) {
        String url = DATASTORE_BASE_URL + "/" + flightId;

        try {
            return restTemplate.getForObject(url, FlightResponse.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Flight not found with id: " + flightId);
        }
    }
    public List<FlightResponse> getAllFlights() {
        FlightResponse[] flightsArray = restTemplate.getForObject(DATASTORE_BASE_URL, FlightResponse[].class);
        return Arrays.asList(flightsArray);
    }

    public FlightResponse addFlight(FlightRequest flightRequest) {
        ResponseEntity<FlightResponse> response = restTemplate.postForEntity(DATASTORE_BASE_URL, flightRequest, FlightResponse.class);
        return response.getBody();
    }

    public FlightResponse updateFlight(Long flightId, FlightRequest flightRequest) {
        // Create the URL to access the flight from TafDatastoreService
        String url = DATASTORE_BASE_URL + "/" + flightId;

        // Send PUT request to TafDatastoreService to update the flight
        restTemplate.put(url, flightRequest);

        // After updating, return the updated flight as a response
        return flightRequest.toFlightResponse(flightId);
    }

    public boolean deleteFlight(Long flightId) {
        String url = DATASTORE_BASE_URL + "/" + flightId;
        try {
            // Check if the flight exists before attempting to delete it
            FlightResponse existingFlight = restTemplate.getForObject(url, FlightResponse.class);

            if (existingFlight != null) {
                // If the flight exists, proceed with deletion
                restTemplate.delete(url);
                return true;
            } else {
                // If the flight doesn't exist, return false
                return false;
            }
        } catch (HttpClientErrorException.NotFound e) {
            // This will catch the 404 error from the REST call and return false
            return false;
        } catch (Exception e) {
            // Log and throw other exceptions if necessary
            throw new RuntimeException("Error deleting flight with ID " + flightId, e);
        }
    }
}