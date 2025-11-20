package com.example.schedules.service;

import com.example.schedules.dto.Comment.*;
import com.example.schedules.entity.Comment;
import com.example.schedules.entity.Schedules;
import com.example.schedules.exception.ErrorCode;
import com.example.schedules.exception.GlobalException;
import com.example.schedules.repository.CommentRepository;
import com.example.schedules.repository.SchedulesRepository;
import com.example.schedules.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.schedules.entity.User;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final SchedulesRepository schedulesRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    //댓글 생성
    @Transactional
    public CreateCommentResponse commentSave(CreateCommentRequest request, Long userId, Long scheduleId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND)
        );

        Schedules schedule = schedulesRepository.findById(scheduleId).orElseThrow(
                () -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        int commentCount = commentRepository.countBySchedulesId(scheduleId);
        if (commentCount >= 10) {
            throw new GlobalException(ErrorCode.COMMENT_LIMIT_EXCEEDED);
        }
        Comment comment = new Comment(request.getCommentContent(), user, schedule);
        Comment savecomment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savecomment.getCommentId(),
                savecomment.getCommentContent(),
                savecomment.getUser().getUsername(),
                savecomment.getCreatedAt(),
                savecomment.getModifiedAt()
        );
    }
    //댓글 조회
    @Transactional
    public List<GetCommentResponse> getComment() {
        List<Comment> comments = commentRepository.findAll();
        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getCommentId(),
                    comment.getCommentContent(),
                    comment.getUser().getUsername(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtos.add(dto);

        }
        return dtos;
    }
    //댓글 수정
    @Transactional
    public UpdateCommentResponse updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND)
        );
        comment.updateComment(
                request.getCommentContent()
        );

        return new UpdateCommentResponse(
                comment.getCommentId(),
                comment.getCommentContent(),
                comment.getUser().getUsername(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );

    }
    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        boolean exit = commentRepository.existsById(commentId);
        if (!exit) {
            throw new GlobalException(ErrorCode.COMMENT_NOT_FOUND);
        }
        commentRepository.deleteById(commentId);
    }
}
