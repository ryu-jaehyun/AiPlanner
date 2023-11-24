package Project.AiPlanner.Plan.Dto.day;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DayPlanUpdateDto {
    @NotNull(message = "일정아이디는 필수 값입니다.")
    private int planId;
    private String planName;
    private String planType;
    private LocalDateTime start;
    private LocalDateTime end;
    private String plan;
}
