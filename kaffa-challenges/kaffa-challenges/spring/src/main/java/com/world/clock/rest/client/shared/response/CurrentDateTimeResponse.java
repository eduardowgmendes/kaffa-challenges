package com.world.clock.rest.client.shared.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrentDateTimeResponse {
    private String fileCurrentDateTime;
    private String currentDateTime;
}
