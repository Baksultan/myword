package com.example.myword.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String email;
    private String username;

}
