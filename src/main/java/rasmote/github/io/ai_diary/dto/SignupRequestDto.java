package rasmote.github.io.ai_diary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    @Size(min = 4, max = 20, message = "사용자 이름은 4자 이상, 20자 이하로 작성해주세요.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 6자 이상으로 작성해주세요.")
    private String password;
}
