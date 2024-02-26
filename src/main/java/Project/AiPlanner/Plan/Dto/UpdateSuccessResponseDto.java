package Project.AiPlanner.Plan.Dto;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateSuccessResponseDto {
    private String message;
    private String result;
}
