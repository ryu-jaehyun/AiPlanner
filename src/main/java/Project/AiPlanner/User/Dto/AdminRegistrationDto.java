package Project.AiPlanner.User.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminRegistrationDto {
    @NotBlank(message = "아이디는 필수 값입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String userPw;

    @NotBlank(message = "관리자 인증 코드는 필수 값입니다.")
    private String adminCode;

    @NotBlank(message = "실명은 필수 값입니다.")
    private String realName;
}
