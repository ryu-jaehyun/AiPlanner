package Project.AiPlanner.User.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserPlanTypeColorDto {

    @NotNull(message = "색깔은 필수 값입니다.")
    private String color;

    @NotNull(message = "일정종류는 필수 값입니다.")
    private String planType;


}
