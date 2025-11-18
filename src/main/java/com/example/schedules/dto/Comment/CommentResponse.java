package com.example.schedules.dto.Comment;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CommentResponse {
    private final Long id;
    private final String commentContent;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponse(Long id, String commentContent, String userName ,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.commentContent = commentContent;
        this.userName = userName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
