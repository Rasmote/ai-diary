package rasmote.github.io.ai_diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import rasmote.github.io.ai_diary.dto.CommonResponseDto;
import rasmote.github.io.ai_diary.exception.CustomException;
import rasmote.github.io.ai_diary.exception.ErrorCode;

@Slf4j
@RestControllerAdvice // @ControllerAdvice는 전역 예외 처리기 역할을 하는 어노테이션
public class GlobalExceptionHandler {
    
    //Service 등에서 우리가 직접 throw를 한 경우
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponseDto<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("CustomException: {} - {}", errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponseDto.failure(errorCode.getCode(), errorCode.getMessage()));
    }

    // @Valid 유효성 검증이 실패한 경우
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponseDto<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException occurred: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE; // ErrorCode에 추가 필요
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponseDto.failure(errorCode.getCode(), errorCode.getMessage()));
    }

    // Spring Security에서 발생하는 경우
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponseDto<Void>> handAccessDeniedException(AccessDeniedException e) {
        log.warn("AccessDeniedException occurred: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.FORBIDDEN_ACCESS; // ErrorCode에 추가 필요
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponseDto.failure(errorCode.getCode(), errorCode.getMessage()));
    }

    // 그 이외 우리가 명시하지 않은 모든 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseDto<Void>> handleException(Exception ex) {
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR; // ErrorCode에 추가 필요
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponseDto.failure(errorCode.getCode(), errorCode.getMessage()));
    }
}
