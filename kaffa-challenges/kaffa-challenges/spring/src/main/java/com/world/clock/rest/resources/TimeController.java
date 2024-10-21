package com.world.clock.rest.resources;

import com.world.clock.rest.client.shared.dto.TimeDTO;
import com.world.clock.rest.client.shared.response.CurrentDateTimeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/datetime/now")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @Operation(summary = "Get current date and time", description = "Fetches the current date and time from the WorldClockAPI or the JVM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched date and time"),
            @ApiResponse(responseCode = "500", description = "Failed to fetch from WorldClockAPI, returning JVM time")
    })
    @GetMapping
    public ResponseEntity<CurrentDateTimeResponse> now() throws IOException {
        TimeDTO timeDTO = timeService.fetchCurrentTime();

        if (timeDTO != null)
            return ResponseEntity
                    .ok(createResponse(timeDTO));

        TimeDTO fileTime = new TimeDTO();
        fileTime.setCurrentDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return ResponseEntity
                .ok(createResponse(fileTime));
    }

    /**
     * Creates a DateTimeResponse based on the TimeDTO
     * passed as a parameter, which comes from a call to a WorldClockAPI,
     * containing several useful date and time properties.
     * <p>
     * This DateTimeResponse will be sent as a response
     * when a request to the /api/v1/datetime/now endpoint occurs.
     * <p>
     * If the WorldClockAPI service fails in any way,
     * the JVM's date and time will be sent as a fallback.
     *
     * @param currentTime - The content body of the call from WorldClockAPI.
     *                    <p>
     *                    If the call is successful, the currentDateTime property
     *                    will be populated with data from the WorldClockAPI.
     *                    <p>
     *                    If the call fails, the fileCurrentDateTime property
     *                    will be populated with the local date and time using
     *                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).
     * @return DateTimeResponse
     */

    private CurrentDateTimeResponse createResponse(TimeDTO currentTime) {
        CurrentDateTimeResponse response = new CurrentDateTimeResponse();

        if (currentTime != null)
            response.setCurrentDateTime(currentTime.getCurrentDateTime());

        response.setFileCurrentDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return response;
    }
}
