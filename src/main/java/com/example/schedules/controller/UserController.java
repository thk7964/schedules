package com.example.schedules.controller;

import com.example.schedules.dto.Schedules.*;
import com.example.schedules.dto.Users.*;
import com.example.schedules.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //유저 생성
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);//생성된 유저 정보와 201 상태코드 반환
    }
    //단건 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser (@PathVariable Long userId) {
        GetOneUserResponse result = userService.getOneUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);//단건 조회 정보와 200 상태코드 반환
    }
    //전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<GetOneUserResponse>> getAllUsers() {
        List<GetOneUserResponse> result = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(result);//전체 조회 정보와 200 상태코드 반환
    }
    //유저 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        UpdateUserResponse result = userService.updateUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);//수정된 유저 정보와 200 상태코드 반환
    }
    //유저 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//삭제 성공시 204 상태코드 반환
    }
}

