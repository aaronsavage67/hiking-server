package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripGroupDto {

    private long id;

    private long tripId;

    private String username;

    private String status;
}

