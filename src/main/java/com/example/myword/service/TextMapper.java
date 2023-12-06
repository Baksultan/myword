package com.example.myword.service;

import com.example.myword.model.dto.TextDto;
import com.example.myword.model.entity.Text;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextMapper {

    private final S3Service s3Service;

    public TextDto toDto(Text text) {

        byte[] obj = s3Service.getObject("myword-bucket", text.getS3Key());
        String content = new String(obj, StandardCharsets.UTF_8);

        TextDto textDto = TextDto.builder()
                .title(text.getTitle())
                .content(content)
                .URL("http://localhost:9000/text/" + text.getURL())
                .creator(text.getCreator().getUsername())
                .build();

        return textDto;
    }

    public List<TextDto> toDtoList(List<Text> texts) {
        return texts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
