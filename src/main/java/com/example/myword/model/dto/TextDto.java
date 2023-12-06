package com.example.myword.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextDto {

    private String title;
    private String content;
    private String URL;
    private String creator;
}
