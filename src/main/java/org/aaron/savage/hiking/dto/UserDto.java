package org.aaron.savage.hiking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private long id;

    private String name;

    private String username;

    private String password;
}
