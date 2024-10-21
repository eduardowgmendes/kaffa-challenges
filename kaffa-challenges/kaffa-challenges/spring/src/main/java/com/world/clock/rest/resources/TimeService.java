package com.world.clock.rest.resources;

import com.world.clock.database.shared.entity.TimeEntity;
import com.world.clock.rest.client.WorldClockService;
import com.world.clock.rest.client.shared.response.WorldClockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class TimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger("TimeService");

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private WorldClockService worldClockService;

    public void fetchCurrentTime() throws IOException {
        Call<WorldClockResponse> call = worldClockService.getCurrentTime();
        Response<WorldClockResponse> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            TimeEntity timeEntity = readBodyOf(response);
            if (timeEntity != null) {
                timeRepository.save(timeEntity);
                LOGGER.info("saved {} on database", timeEntity);
            } else {
                LOGGER.error("timeUnit is null");
            }
        } else {
            LOGGER.info("failed to obtain data from WorldClock API");
        }
    }

    private static TimeEntity readBodyOf(Response<WorldClockResponse> response) {
        WorldClockResponse worldClockResponse = response.body();

        if (worldClockResponse != null) {
            TimeEntity timeEntity = new TimeEntity();
            timeEntity.setCurrentDateTime(worldClockResponse.getCurrentDateTime());
            timeEntity.setUtcOffset(worldClockResponse.getUtfOffset());
            timeEntity.setDayLightTimeSavingsTime(worldClockResponse.isDayLightSavingsTime());
            timeEntity.setDayOfTheWeek(worldClockResponse.getDayOfTheWeek());
            timeEntity.setTimeZoneName(worldClockResponse.getTimeZoneName());
            return timeEntity;
        }

        return null;

    }
}
