package com.example.schedules.service;

import com.example.schedules.dto.Schedules.*;
import com.example.schedules.entity.Schedules;
import com.example.schedules.repository.SchedulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulesService {
    private final SchedulesRepository schedulesRepository;

    //일정 생성
    @Transactional
    public CreateSchedulesResponse save(CreateSchedulesRequest request){
        Schedules schedule = new Schedules(request.getTitle(),request.getContent(),request.getName());
        Schedules saveSchedule = schedulesRepository.save(schedule);

        return new CreateSchedulesResponse(
                saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getName(),
                saveSchedule.getCreatedAt(),
                saveSchedule.getModifiedAt()
        );

    }
    //일정 단 건 조회
    @Transactional(readOnly = true)
    public GetOneSchedulesResponse getOneSchedule(Long scheduleId){
        //일정이 없으면 예외 발생
        Schedules schedule = schedulesRepository.findById(scheduleId).orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 일정입니다.")
        );
        return new GetOneSchedulesResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // 일정 전체 조회
    @Transactional(readOnly = true)
    public List<GetOneSchedulesResponse> getAllSchedule(){
        List<Schedules> schedules= schedulesRepository.findAll();

        List<GetOneSchedulesResponse> dtos = new ArrayList<>();
        for (Schedules schedule : schedules){
            GetOneSchedulesResponse dto =new GetOneSchedulesResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getName(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }
    //일정 수정
    @Transactional
    public UpdateSchedulesResponse update(Long scheduleId, UpdateSchedulesRequest request){
        //수정할 일정이 없으면 예외 발생
        Schedules schedules=  schedulesRepository.findById(scheduleId).orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        //제목과 작성자명 업데이트
        schedules.update(
                request.getTitle(),
                request.getContent()
        );
        return new UpdateSchedulesResponse(
                schedules.getId(),
                schedules.getTitle(),
                schedules.getContent(),
                schedules.getName(),
                schedules.getCreatedAt(),
                schedules.getModifiedAt()
        );
    }
    //일정 삭제
    @Transactional
    public void delete(Long scheduleId){
        //해당 일정 존재유무 확인 없으면 예외 발생
        boolean existence = schedulesRepository.existsById(scheduleId);
        if(!existence){
            throw new IllegalStateException("존재하지 않는 일정입니다..");
        }
        //존재하면 삭제
        schedulesRepository.deleteById(scheduleId);
    }
}
