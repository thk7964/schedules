package com.example.schedules.dto.Schedules;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class GetOneSchedulesResponse extends SchedulesResponse{
    public GetOneSchedulesResponse(Long id, String title, String content, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(id, title, content, name, createdAt, modifiedAt);
    }
}
