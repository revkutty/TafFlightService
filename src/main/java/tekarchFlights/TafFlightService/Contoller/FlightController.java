package tekarchFlights.TafFlightService.Contoller;


import org.springframework.http.HttpStatus;
import tekarchFlights.TafFlightService.DTO.FlightRequest;
import tekarchFlights.TafFlightService.DTO.FlightResponse;
import tekarchFlights.TafFlightService.Service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "*") // Enable CORS for all origins
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Search for flights with query parameters
    @GetMapping("/search")
    public List<?> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String date) {
        return flightService.searchFlights(origin, destination, date);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long flightId) {
        try {
            FlightResponse flight = flightService.getFlightById(flightId);
            return ResponseEntity.ok(flight);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get all flights
    @GetMapping("/all")
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        List<FlightResponse> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @PostMapping
    public ResponseEntity<FlightResponse> addFlight(@Valid @RequestBody FlightRequest flightRequest) {
        FlightResponse flight = flightService.addFlight(flightRequest);
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long flightId, @Valid @RequestBody FlightRequest flightRequest) {
        // Assuming the flight service method is correctly updating the flight in the database
        FlightResponse updatedFlight = flightService.updateFlight(flightId, flightRequest);

        // Return the updated flight with status 200 OK
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long flightId) {
        boolean isDeleted = flightService.deleteFlight(flightId);

        if (isDeleted) {
            return ResponseEntity.ok("Flight with ID " + flightId + " successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Flight with ID " + flightId + " not found.");
        }
    }
}