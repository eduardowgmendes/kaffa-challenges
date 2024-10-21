package com.world.clock.rest.client.shared.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorldClockResponse {

    private String currentDateTime;
    private String utfOffset;
    private boolean isDayLightSavingsTime;
    private String dayOfTheWeek;
    private String timeZoneName;

}
