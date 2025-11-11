package com.example.schedules.service;

import com.example.schedules.dto.Users.*;
import com.example.schedules.entity.User;
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
        User user = new User(request.getUsername(), request.getEmail());
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
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
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
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
        user.update(
                request.getUsername(),
                request.getEmail()
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
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        }
        userRepository.deleteById(userId);
    }
}
