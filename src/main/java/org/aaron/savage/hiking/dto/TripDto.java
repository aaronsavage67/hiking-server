package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TripDto {

    private long id;

    private List<String> usernames;

    private long mountainId;

    private String mountainName;

    private String date;
}
