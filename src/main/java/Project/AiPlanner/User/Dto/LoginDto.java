package Project.AiPlanner.User.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    //@JsonProperty("userId")   //이거 스프링 시ㅋ큐리티 때문에 username이 userrid로 개념이 바껴서 매핑되는데 현업에서는 어떯게 개발하는지 알아보기
    @NotNull
    @Size(min = 2, max = 50)
    private String userId;

    @NotNull
    @Size(min = 1, max = 100)
    private String userPw;
}