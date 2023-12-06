package com.example.myword.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextRequestDto {
    private String title;
    private String content;
    private String creator;
}
