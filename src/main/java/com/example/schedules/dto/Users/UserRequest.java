package com.example.schedules.dto.Users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequest {
    @Size(max = 4)
    @NotBlank(message = "유저명은 필수 입력 값입니다.")
    private String username;
    @NotBlank(message = "이메일은  필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
