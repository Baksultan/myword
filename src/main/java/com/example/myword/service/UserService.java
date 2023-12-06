package com.example.myword.service;

import com.example.myword.model.dto.RegistrationUserDto;
import com.example.myword.model.dto.UserDto;
import com.example.myword.model.entity.Text;
import com.example.myword.model.entity.User;
import com.example.myword.model.exception.AppError;
import com.example.myword.repo.RoleRepo;
import com.example.myword.repo.UserRepo;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    private final RoleRepo roleRepo;

    public Optional<User> findByEmail(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getUserRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User getUserByUsername(String username) {
        User user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return user;
    }

    public ResponseEntity<?> userRegister(RegistrationUserDto userDto) {

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(400, "Passwords did not match"), HttpStatus.BAD_REQUEST);
        }

        User userNameExist = userRepo.findByUsername(userDto.getUsername()).orElse(null);
        if (userNameExist != null) {
            return new ResponseEntity<>(new AppError(400, "Username already exists"), HttpStatus.BAD_REQUEST);
        }

        User emailExist = userRepo.findByEmail(userDto.getEmail()).orElse(null);
        if (emailExist != null) {
            return new ResponseEntity<>(new AppError(400, "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .texts(new ArrayList<Text>())
                .userRoles(List.of(roleRepo.findByName("ROLE_USER").get()))
                .build();
        User u = userRepo.save(user);
        if (u.getId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(u));
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong during registration"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> userList = userRepo.findAll();
        if (!userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(userMapper.toDtoList(userList));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<UserDto>());
    }

    public ResponseEntity<UserDto> getUserById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(userMapper.toDto(user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }



}
