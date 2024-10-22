package com.world.clock.rest.client;

import com.world.clock.rest.client.shared.response.WorldClockResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WorldClockService {

    @GET("/api/json/utc/now")
    Call<WorldClockResponse> getCurrentTime();

}
