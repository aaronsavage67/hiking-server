package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripDto {

    private long id;

    private long organiserId;

    private long mountainId;

    private String date;

    private String description;
}
