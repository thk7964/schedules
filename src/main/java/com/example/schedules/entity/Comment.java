package com.example.schedules.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId", nullable = false)
    private Schedules schedules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Comment(String commentContent, User user,Schedules schedules){
        this.commentContent=commentContent;
        this.user=user;
        this.schedules=schedules;
    }

    public void updateComment(String commentContent){
        this.commentContent=commentContent;
    }
}
