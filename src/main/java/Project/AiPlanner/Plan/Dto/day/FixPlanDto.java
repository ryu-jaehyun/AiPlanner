package Project.AiPlanner.Plan.Dto.day;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FixPlanDto {


    @NotNull(message = "아이디는 필수 값입니다.")
    private String userId;

    @NotNull(message = "일정이름은 필수 값입니다.")
    private String planName;

    @NotNull(message = "일정종류는 필수 값입니다.")
    private String planType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "시작시간은 필수 값입니다.")
    private LocalDateTime start;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "종료시간은 필수 값입니다.")
    private LocalDateTime end;




}
