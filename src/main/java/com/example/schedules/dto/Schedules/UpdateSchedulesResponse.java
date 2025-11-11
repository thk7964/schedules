package com.example.schedules.dto.Schedules;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class UpdateSchedulesResponse extends SchedulesResponse{
    public UpdateSchedulesResponse(Long id, String title, String content, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(id, title, content, name, createdAt, modifiedAt);
    }
}
