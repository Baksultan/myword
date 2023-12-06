package com.example.myword.controller;

import com.example.myword.model.dto.TextDto;
import com.example.myword.model.dto.UserDto;
import com.example.myword.service.TextService;
import com.example.myword.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminAPI {

    private final UserService userService;
    private final TextService textService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/texts")
    public ResponseEntity<List<TextDto>> getAllTexts() {
        return textService.getAllTexts();
    }

    @GetMapping(value = "/texts/{url}")
    public ResponseEntity<TextDto> getText(@PathVariable(name = "url") String url) {
        return textService.getText(url);
    }


}
