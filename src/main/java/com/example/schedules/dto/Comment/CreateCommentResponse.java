package com.example.schedules.dto.Comment;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CreateCommentResponse extends CommentResponse{
    public CreateCommentResponse(Long id, String commentContent, String username, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(id, commentContent, username,createdAt, modifiedAt);
    }
}
