package com.world.clock.rest.configuration;

import com.world.clock.rest.client.WorldClockService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class RetrofitConfiguration {

    @Value("${worldclockapi.baseurl}")
    private String baseUrl;

    @Bean
    public Retrofit retrofit() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Bean
    public WorldClockService worldClockService(Retrofit retrofit) {
        return retrofit.create(WorldClockService.class);
    }
}
