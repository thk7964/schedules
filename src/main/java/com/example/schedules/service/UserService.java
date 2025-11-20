package com.example.schedules.service;

import com.example.schedules.config.PasswordEncoder;
import com.example.schedules.dto.Users.*;
import com.example.schedules.entity.User;
import com.example.schedules.exception.ErrorCode;
import com.example.schedules.exception.GlobalException;
import com.example.schedules.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //유저 생성
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        //이전에 생성된 중복된 이메일을 확인한다.
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new GlobalException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        // 비밀번호 암호화 후 유저 생성 및 저장한다.
        String passwordEncoded = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), request.getEmail(), passwordEncoded);
        User saveUser = userRepository.save(user);


        return new CreateUserResponse(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getEmail(),
                saveUser.getCreatedAt(),
                saveUser.getModifiedAt()
        );
    }

    //유저 단건 조회
    @Transactional(readOnly = true)
    public GetOneUserResponse getOneUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND)
        );
        return new GetOneUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
    //유저 전체 조회
    @Transactional(readOnly = true)
    public List<GetOneUserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<GetOneUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetOneUserResponse dto = new GetOneUserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }
    //유저 수정
    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        //이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new GlobalException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        //유저 조회
        User user = userRepository.findById(userId).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND)
        );
        user.update(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
    //유저 삭제
    @Transactional
    public void deleteUser(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if (!existence) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }
    // 로그인
    @Transactional
    public User userLogin(LoginRequest request) {
        // 이메일 존재 여부 확인
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new GlobalException(ErrorCode.EMAIL_NOT_FOUND)
        );
        //비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GlobalException(ErrorCode.INVALID_SCHEDULE_PASSWORD);
        }
        return user;
    }
}
