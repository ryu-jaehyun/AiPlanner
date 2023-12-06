package Project.AiPlanner.Plan.Dto.month;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MonthPlanUpdateDto {
    @NotNull(message = "일정아이디는 필수 값입니다.")
    private int planId;
    private String planName;
    private String planType;
    private LocalDate start;
    private LocalDate end;
    private String color;
    private int success;
    private String dayOfWeek;
}