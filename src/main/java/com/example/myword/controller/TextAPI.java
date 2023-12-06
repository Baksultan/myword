package com.example.myword.controller;

import com.example.myword.model.dto.TextDto;
import com.example.myword.model.dto.TextRequestDto;
import com.example.myword.model.entity.User;
import com.example.myword.service.TextService;
import com.example.myword.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/text")
public class TextAPI {

    private final TextService textService;
    private final UserService userService;

    @GetMapping(value = "/{url}")
    public ResponseEntity<TextDto> getText(@PathVariable(name = "url") String url) {
        return textService.getText(url);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<TextDto>> getUserTexts(Principal principal) {
        return textService.getUserTexts(principal);
    }

    @PostMapping
    public ResponseEntity<TextDto> addNewText(@RequestBody TextRequestDto textRequestDto) {
        return textService.createNewText(textRequestDto);
    }

}
