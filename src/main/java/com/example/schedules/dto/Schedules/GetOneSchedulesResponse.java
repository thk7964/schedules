package com.example.schedules.dto.Schedules;

import com.example.schedules.dto.Comment.CommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetOneSchedulesResponse extends SchedulesResponse{
    private final  List<CommentResponse> comments;

    public GetOneSchedulesResponse(Long id, String title, String content, String username, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentResponse> comments) {
        super(id, title, content, username, createdAt, modifiedAt );
        this.comments=comments;
    }

    public GetOneSchedulesResponse(Long id, String title, String content, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(id, title, content, name, createdAt, modifiedAt);
        this.comments=null;
    }


}
