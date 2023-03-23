package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MunroBagDto {

    private long id;

    private String username;

    private long mountainId;

    private String mountainName;

    private String date;

    private String rating;
}
