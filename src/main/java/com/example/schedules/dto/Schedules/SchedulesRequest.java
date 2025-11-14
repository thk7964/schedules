package com.example.schedules.dto.Schedules;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SchedulesRequest {
    @Size (max=10)
    @NotBlank(message = "일정 제목은 필수 입력 값입니다.")
    private String title;
    @NotBlank(message = "일정 내용은 필수 입력 값입니다.")
    private String content;
}
