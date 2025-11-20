package com.example.schedules.service;

import com.example.schedules.dto.Comment.CommentResponse;
import com.example.schedules.dto.Schedules.*;
import com.example.schedules.entity.Comment;
import com.example.schedules.entity.Schedules;
import com.example.schedules.entity.User;
import com.example.schedules.exception.ErrorCode;
import com.example.schedules.exception.GlobalException;
import com.example.schedules.repository.CommentRepository;
import com.example.schedules.repository.SchedulesRepository;
import com.example.schedules.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor// 필요한 의존성 자동 주입
public class SchedulesService {
    private final SchedulesRepository schedulesRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    //일정 생성
    @Transactional
    public CreateSchedulesResponse save(CreateSchedulesRequest request, Long userId) {
        // 작성한 유저가 실제 존재한는지 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND)
        );

        Schedules schedule = new Schedules(request.getTitle(), request.getContent(), user);
        Schedules saveSchedule = schedulesRepository.save(schedule);

        return new CreateSchedulesResponse(
                saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getUser().getUsername(),
                saveSchedule.getCreatedAt(),
                saveSchedule.getModifiedAt()
        );

    }

    //일정 단 건 조회
    @Transactional(readOnly = true)
    public GetOneSchedulesResponse getOneSchedule(Long scheduleId) {

        //일정이 없으면 예외 발생
        Schedules schedule = schedulesRepository.findById(scheduleId).orElseThrow(
                () -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        List <Comment> comments= commentRepository.findAllBySchedulesId(scheduleId);
        List <CommentResponse> commentResponses= comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getCommentId(),
                        comment.getCommentContent(),
                        comment.getUser().getUsername(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).toList();
        if(!commentResponses.isEmpty()){//댓글이 하나 이상 있을 때 댓글을 포함해서 반환
            return new GetOneSchedulesResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getUser().getUsername(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt(),
                    commentResponses
            );
        }
        return new GetOneSchedulesResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // 일정 전체 조회
    @Transactional(readOnly = true)
    public List<GetOneSchedulesResponse> getAllSchedule() {
        List<Schedules> schedules = schedulesRepository.findAll();

        List<GetOneSchedulesResponse> dtos = new ArrayList<>();
        for (Schedules schedule : schedules) {
            GetOneSchedulesResponse dto = new GetOneSchedulesResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getUser().getUsername(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    //일정 수정
    @Transactional
    public UpdateSchedulesResponse update(Long scheduleId, UpdateSchedulesRequest request) {

        //수정할 일정이 없으면 예외 발생
        Schedules schedules = schedulesRepository.findById(scheduleId).orElseThrow(
                () -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND)
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
                schedules.getUser().getUsername(),
                schedules.getCreatedAt(),
                schedules.getModifiedAt()
        );
    }

    //일정 삭제
    @Transactional
    public void delete(Long scheduleId) {
        //해당 일정 존재유무 확인 없으면 예외 발생
        boolean existence = schedulesRepository.existsById(scheduleId);
        if (!existence) {
            throw new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND);
        }
        //댓글 삭제
        commentRepository.deleteAllBySchedulesId(scheduleId);
        //존재하면 삭제
        schedulesRepository.deleteById(scheduleId);
    }
}
