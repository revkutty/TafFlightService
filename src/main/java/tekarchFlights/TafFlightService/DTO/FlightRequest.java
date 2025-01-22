package tekarchFlights.TafFlightService.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotBlank(message = "Departure location is required")
    private String departure;

    @NotBlank(message = "Arrival location is required")
    private String arrival;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;  // Changed to LocalDateTime

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;  // Changed to LocalDateTime

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Available seats are required")
    private Integer availableSeats;

    // Convert FlightRequest to FlightResponse
    public FlightResponse toFlightResponse(Long id) {
        return new FlightResponse(
                id,
                this.flightNumber,
                this.departure,
                this.arrival,
                this.departureTime,
                this.arrivalTime,
                this.price,
                this.availableSeats
        );
    }
}

