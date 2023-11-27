package Project.AiPlanner.User.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserPwRequestDto {
    @NotNull(message = "아이디는 필수 값입니다.")
    private String userId;
    @NotNull(message = "핸드폰번호는 필수 값입니다.")
    private String phoneNum;
}
