package Project.AiPlanner.Plan.Dto.day;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class DaySuccessDto {

    private Integer planId;
    private String userId;
    private String planName;
    private String planType;
    private LocalDateTime start;
    private LocalDateTime end;
    private String plan;
    private int success;
}
