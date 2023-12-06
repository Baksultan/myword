package com.example.myword.service;

import com.example.myword.model.dto.UserDto;
import com.example.myword.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        return userDto;
    }

    public List<UserDto> toDtoList(List<User> userList) {
        return userList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
