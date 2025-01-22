package tekarchFlights.TafFlightService.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class FlightResponse {

    private Long id;
    private String flightNumber;
    private String departure;
    private String arrival;
    private String departureTime;
    private String arrivalTime;
    private Double price;
    private Integer availableSeats;

    // Constructor that accepts LocalDateTime for departure and arrival times
    public FlightResponse(Long id, String flightNumber, String departure, String arrival,
                          LocalDateTime departureTime, LocalDateTime arrivalTime,
                          Double price, Integer availableSeats) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        // Format LocalDateTime to String
        this.departureTime = formatDateTime(departureTime);
        this.arrivalTime = formatDateTime(arrivalTime);
        this.price = price;
        this.availableSeats = availableSeats;
    }

    // Utility method to format LocalDateTime to String
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return dateTime != null ? dateTime.format(formatter) : null;
    }

    // Default constructor if needed (but it's usually good practice to provide one that initializes all fields)
    public FlightResponse() {}
}