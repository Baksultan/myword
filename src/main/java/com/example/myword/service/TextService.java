package com.example.myword.service;

import com.example.myword.model.dto.TextDto;
import com.example.myword.model.dto.TextRequestDto;
import com.example.myword.model.entity.Text;
import com.example.myword.model.entity.User;
import com.example.myword.repo.TextRepo;
import com.example.myword.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextService {

    private final TextRepo textRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    private final TextMapper textMapper;
    private final S3Service s3Service;

    public ResponseEntity<TextDto> getText(String URL) {
        Text text = textRepo.findByURL(URL);
        if (text != null) {
            text.setViews(text.getViews()+1);
            textRepo.save(text);
            return ResponseEntity.status(HttpStatus.FOUND).body(textMapper.toDto(text));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<List<TextDto>> getAllTexts() {
        List<Text> texts = textRepo.findAll();
        if (!texts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(textMapper.toDtoList(texts));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<TextDto>());
    }


    public ResponseEntity<List<TextDto>> getUserTexts(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String username = principal.getName();

        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Text> texts = textRepo.findAllByCreator(user);
        List<TextDto> dtoList = textMapper.toDtoList(texts);

        return ResponseEntity.status(HttpStatus.OK).body(dtoList);

    }


    public ResponseEntity<TextDto> createNewText(TextRequestDto textRequestDto, Principal principal) {
        String s3Key = UUID.randomUUID().toString();
        byte[] content = textRequestDto.getContent().getBytes();
        s3Service.putObject("myword-bucket", s3Key, content);

        User creator = userRepo.findByUsername(principal.getName()).orElse(null);

        if (creator != null) {
            Text text = Text.builder()
                    .title(textRequestDto.getTitle())
                    .s3Key(s3Key)
                    .creator(creator)
                    .views(0L)
                    .build();
            Text t = textRepo.save(text);
            if (t.getId() != null && t.getCreator()!=null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(textMapper.toDto(t));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
