package Project.AiPlanner.Plan.Dto.month;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MonthSuccessDto {

    private Integer planId;
    private String userId;
    private String planName;
    private String planType;
    private LocalDate start;
    private LocalDate end;

    private int success;
    private String color;

}

