package com.example.schedules.dto.Users;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class GetOneUserResponse extends UserResponse {
    public GetOneUserResponse(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(id, username, email, createdAt, modifiedAt);
    }
}
