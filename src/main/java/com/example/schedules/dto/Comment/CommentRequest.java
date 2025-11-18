package com.example.schedules.dto.Comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequest {
    @NotBlank(message = "댓글은 필수 입력 값입니다.")
    private String commentContent;
}
