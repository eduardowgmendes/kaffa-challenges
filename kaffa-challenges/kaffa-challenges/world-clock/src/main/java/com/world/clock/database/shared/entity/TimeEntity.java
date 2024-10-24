package com.world.clock.database.shared.entity;

import com.world.clock.rest.client.shared.dto.TimeDTO;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "time_history")
public class TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_date_time")
    private String currentDateTime;

    @Column(name = "is_day_light_time_savings_time")
    private boolean isDayLightTimeSavingsTime;

    @Column(name = "day_of_the_week")
    private String dayOfTheWeek;

    @Column(name = "utc_offset")
    private String utcOffset;

    @Column(name = "time_zone_name")
    private String timeZoneName;

    public static TimeEntity from(TimeDTO timeDTO) {
        return new ModelMapper().map(timeDTO, TimeEntity.class);
    }

}
