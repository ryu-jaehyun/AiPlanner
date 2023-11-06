package Project.AiPlanner.User.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 2, max = 50)
    private String userName;

    @NotNull
    @Size(min = 1, max = 100)
    private String userPw;
}