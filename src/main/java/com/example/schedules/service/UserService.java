package com.example.schedules.service;

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


    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(request.getUsername(), request.getEmail(),request.getPassword());
        User saveUser = userRepository.save(user);
        return new CreateUserResponse(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getEmail(),
                saveUser.getCreatedAt(),
                saveUser.getModifiedAt()
        );
    }


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


    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
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


    @Transactional
    public void deleteUser(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if(!existence){
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public User userLogin(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new GlobalException(ErrorCode.EMAIL_NOT_FOUND)
        );
        if (!user.getPassword().equals(request.getPassword())){
            throw new GlobalException(ErrorCode.INVALID_SCHEDULE_PASSWORD);
        }
        return user;
    }
}
