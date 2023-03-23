package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {

    private long id;

    private String username;

    private String reviewDate;

    private long mountainId;

    private String mountainName;

    private String walkDate;

    private String rating;

    private String comment;
}