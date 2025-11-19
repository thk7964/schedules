package com.example.schedules.controller;

import com.example.schedules.dto.Schedules.*;
import com.example.schedules.service.SchedulesService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SchedulesController {
    private final SchedulesService schedulesService;

    //일정 생성
    @PostMapping("/schedules")
    public ResponseEntity<CreateSchedulesResponse> createSchedule(@Valid @RequestBody CreateSchedulesRequest request, HttpSession session) {
        Long loginUser = (Long) session.getAttribute("loginUser");

        CreateSchedulesResponse result = schedulesService.save(request, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);//생성된 일정 정보와 201 상태코드 반환
    }

    //단건 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetOneSchedulesResponse> getOneScheduleResponse(@PathVariable Long scheduleId) {
        GetOneSchedulesResponse result = schedulesService.getOneSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);//단건 조회 정보와 200 상태코드 반환
    }

    //전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetOneSchedulesResponse>> getAllSchedule() {
        List<GetOneSchedulesResponse> result = schedulesService.getAllSchedule();
        return ResponseEntity.status(HttpStatus.OK).body(result);//전체 조회 정보와 200 상태코드 반환
    }

    //일정 수정
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateSchedulesResponse> update(@PathVariable Long scheduleId, @Valid @RequestBody UpdateSchedulesRequest request) {
        UpdateSchedulesResponse result = schedulesService.update(scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);//수정된 일정 정보와 200 상태코드 반환
    }

    //일정 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        schedulesService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//삭제 성공시 204 상태코드 반환
    }
}
