package com.example.schedules.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data //클래스 모든 필드에 게터, 세터, toString 자동 생성
@Builder//객체 생성시 가독성을 향상 시키기 위해 빌더 패턴을 자동 생성
public class ErrorResponseEntity {
    private int status;//HTTP 상태코드
    private String name;//예외 이름
    private String message;//예외 메세지

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .message(e.getMessage())
                        .build());
    }
}