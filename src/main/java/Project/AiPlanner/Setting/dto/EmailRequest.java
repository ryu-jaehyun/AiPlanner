package Project.AiPlanner.Setting.dto;


import lombok.Data;

@Data
public class EmailRequest {
    private String from;
    private String to;
    private String subject;
    private String body;
}