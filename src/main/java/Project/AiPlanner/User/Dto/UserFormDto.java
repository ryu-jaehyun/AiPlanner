package Project.AiPlanner.User.Dto;

import Project.AiPlanner.User.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserFormDto {

    @NotNull(message = "이름은 필수 값입니다.") //빈칸과 null 허용x
    @Length
    //@JsonProperty("userName")
    private String userId;

    @NotNull(message = "비밀번호는 필수 값입니다.")
    private String userPw;

    @NotNull(message = "아이디는 필수 값입니다.")
    //@JsonProperty("userId")
    private String userName;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // 날짜 형식 지정
    @NotNull(message = "생일은 필수 값입니다.")
    private Date birth;

    @NotNull(message = "성별은 필수 값입니다.")
    private char sex;

    @NotNull(message = "학생구분은 필수 값입니다.")
    private String student;

    @NotNull(message = "전화번호는 필수 값입니다.")
    private String phoneNum;


    private Set<AuthorityDto> authorityDtoSet;

    public static UserFormDto from(UserEntity user) {
        if (user == null) return null;

        return UserFormDto.builder()
                .userName(user.getUserName())
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .birth(user.getBirth())
                .sex(user.getSex())
                .student(user.getStudent())
                .phoneNum(user.getPhoneNum())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}

