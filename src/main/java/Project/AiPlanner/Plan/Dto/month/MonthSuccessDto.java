package Project.AiPlanner.Plan.Dto.month;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MonthSuccessDto {

    private Integer planId;
    private String userId;
    private String planName;
    private String planType;
    private LocalDateTime start;
    private LocalDateTime end;

    private int success;
}

