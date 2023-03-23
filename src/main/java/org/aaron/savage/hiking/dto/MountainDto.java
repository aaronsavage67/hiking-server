package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MountainDto {

    private long id;

    private String name;

    private String height;

    private String description;

    private String region;

    private String coords;
}
