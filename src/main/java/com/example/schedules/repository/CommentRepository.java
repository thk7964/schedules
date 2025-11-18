package com.example.schedules.repository;

import com.example.schedules.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countBySchedulesId(Long scheduleId);// 댓글 개수 조회
    void deleteAllBySchedulesId(Long scheduleId);//댓글 삭제
    List<Comment> findAllBySchedulesId(Long scheduleId);//일정에 달린 모든 댓글 조회
}

