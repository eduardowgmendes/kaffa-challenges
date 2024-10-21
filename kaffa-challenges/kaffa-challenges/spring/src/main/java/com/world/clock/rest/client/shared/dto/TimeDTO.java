package com.world.clock.rest.client.shared.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TimeDTO {

    private String currentDateTime;
    private String utfOffset;
    private boolean isDayLightSavingsTime;
    private String dayOfTheWeek;
    private String timeZoneName;
}
