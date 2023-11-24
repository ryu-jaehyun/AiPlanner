package Project.AiPlanner.Plan.Dto.month;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MonthPlanDeleteDto {


    @NotNull(message = "일정아이디는 필수 값입니다.")
    private Integer planId;
}