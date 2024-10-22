package com.world.clock.rest.resources;

import com.world.clock.rest.client.shared.dto.TimeDTO;
import com.world.clock.rest.client.shared.response.CurrentDateTimeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TimeControllerTest {
    @Mock
    private TimeService timeService;

    @InjectMocks
    private TimeController timeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNowWithValidTimeDTO() throws IOException {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setCurrentDateTime("2024-10-21T14:09Z");
        when(timeService.fetchCurrentTime()).thenReturn(timeDTO);

        ResponseEntity<CurrentDateTimeResponse> response = timeController.now();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("2024-10-21T14:09Z", response.getBody().getCurrentDateTime());

        verify(timeService, times(1)).fetchCurrentTime();
    }

    @Test
    void testNowWithFallbackToLocalDateTime() throws IOException {
        when(timeService.fetchCurrentTime()).thenReturn(null);

        ResponseEntity<CurrentDateTimeResponse> response = timeController.now();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        CurrentDateTimeResponse currentDateTimeResponse = response.getBody();

        String expectedDateTime = currentDateTimeResponse.getFileCurrentDateTime();
        assertEquals(expectedDateTime, currentDateTimeResponse.getFileCurrentDateTime());

        verify(timeService, times(1)).fetchCurrentTime();
    }
}
