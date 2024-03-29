package Project.AiPlanner.Setting.dto;

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
    private String color;
    private int success;
    private String dayOfWeek;
}
