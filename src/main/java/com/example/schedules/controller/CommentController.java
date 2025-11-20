package com.example.schedules.controller;

import com.example.schedules.dto.Comment.*;
import com.example.schedules.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // final 필드를 자동으로 생성자 주입
public class CommentController {
    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@PathVariable Long scheduleId, @Valid @RequestBody CreateCommentRequest request, HttpSession session) {
        Long loginUser = (Long) session.getAttribute("loginUser");//로그인한 사용자 id
        CreateCommentResponse commentResult = commentService.commentSave(request, loginUser, scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResult);
    }

    //댓글 전체 조회
    @GetMapping("/comments")
    public ResponseEntity<List<GetCommentResponse>> getComment() {
        List<GetCommentResponse> result = commentService.getComment();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse result = commentService.updateComment(commentId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
