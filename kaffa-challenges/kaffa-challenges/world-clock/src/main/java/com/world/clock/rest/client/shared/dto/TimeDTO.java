package com.world.clock.rest.client.shared.dto;

import com.world.clock.database.shared.entity.TimeEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static TimeDTO from(TimeEntity time) {
        return new ModelMapper().map(time, TimeDTO.class);
    }

}
