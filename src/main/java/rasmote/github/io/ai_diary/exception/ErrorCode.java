package rasmote.github.io.ai_diary.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    
    // 400 Bad Request
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E001", "입력값이 올바르지 않습니다."),
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "E002", "이미 존재하는 사용자 이름입니다."),

    // 401 Unauthorized
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "E003", "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "E006", "권한이 없는 접근입니다."),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E004", "해당 사용자를 찾을 수 없습니다."),
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "E005", "해당 일기를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}