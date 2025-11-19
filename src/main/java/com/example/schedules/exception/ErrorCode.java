package com.example.schedules.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter//클래스 모든 필드에 대해 자동으로 게터 메서드를 생성
@AllArgsConstructor//모든 필드를 매개변수로 받는 생성자를 자동생성
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    INVALID_SCHEDULE_PASSWORD(HttpStatus.UNAUTHORIZED,  "비밀번호가 일치하지 않습니다."),
    COMMENT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST,  "댓글은 최대 10개까지만 작성할 수 있습니다."),
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED,  "로그인 완료 후 사용 가능합니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,  "이미 등록된 이메일 입니다.");


    private final HttpStatus httpStatus;	// HttpStatus
    private final String message;			// 설명

}