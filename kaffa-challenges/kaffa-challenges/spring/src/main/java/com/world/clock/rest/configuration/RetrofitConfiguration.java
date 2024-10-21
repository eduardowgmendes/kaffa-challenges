package com.world.clock.rest.configuration;

import com.world.clock.rest.client.WorldClockService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfiguration {

    @Value("${worldclockapi.baseurl}")
    private String baseUrl;

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }

    @Bean
    public WorldClockService worldClockService(Retrofit retrofit) {
        return retrofit.create(WorldClockService.class);
    }
}
