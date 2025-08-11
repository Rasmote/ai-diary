package rasmote.github.io.ai_diary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto<T> {
    private final boolean success;
    private final T data;
    private final ErrorResponse error;

    public static <T> CommonResponseDto<T> success(T data) {
        return new CommonResponseDto<>(true, data, null);
    }

    public static <T> CommonResponseDto<T> failure(String code, String message) {
        return new CommonResponseDto<>(false, null, new ErrorResponse(code, message));
    }

    @Getter
    @AllArgsConstructor
    private static class ErrorResponse {
        private final String code;
        private final String message;
    }
}
