package Project.AiPlanner.Plan.Dto.month;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MonthPlanDto {





    @NotNull(message = "일정이름은 필수 값입니다.")
    private String planName;

    @NotNull(message = "일정종류는 필수 값입니다.")
    private String planType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "시작날짜는 필수 값입니다.")
    private LocalDate start;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "종료날짜는 필수 값입니다.")
    private LocalDate end;

    @NotNull(message = "일정구분 색값은 필수값 입니다.")
    private String color;

    private Integer success;


}