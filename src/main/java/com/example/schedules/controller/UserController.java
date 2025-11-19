package com.example.schedules.controller;

import com.example.schedules.dto.Schedules.*;
import com.example.schedules.dto.Users.*;
import com.example.schedules.entity.User;
import com.example.schedules.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //유저 생성
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
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
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long userId,@Valid @RequestBody UpdateUserRequest request) {
        UpdateUserResponse result = userService.updateUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);//수정된 유저 정보와 200 상태코드 반환
    }
    //유저 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//삭제 성공시 204 상태코드 반환
    }
    //로그인
    @PostMapping("/users/login")
    public ResponseEntity<Void> userLogin(@Valid @RequestBody LoginRequest request, HttpSession session) {
        if (session.getAttribute("loginUser") != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 로그인된 사용자입니다.");
        }

        User user = userService.userLogin(request);
        session.setAttribute("loginUser", user.getId());//로그인한 사용자 정보가 서버 메모리에 세션으로 저장된다.
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/users/logout")
    public ResponseEntity<Void> logout(HttpSession session){

        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

