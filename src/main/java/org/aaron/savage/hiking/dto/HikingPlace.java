package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HikingPlace {
    private String name;
    private String description;
}
