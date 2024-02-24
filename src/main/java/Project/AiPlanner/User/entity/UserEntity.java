package Project.AiPlanner.User.entity;

import Project.AiPlanner.User.Dto.UserFormDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
@Table(name = "user_information")
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "user_password", nullable = false, length = 100)
    private String userPw;

    @Column(name = "user_name", nullable = false, length = 10)
    private String userName;

    @Column(name = "birth",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birth;

    @Column(name = "sex", nullable = false, length = 1)
    private char sex;

    @Column(name = "student", nullable = false,length = 20)
    private String student;

    @Column(name = "phonenumber",nullable = false, length = 20)
    private String phoneNum;

    @Column(name = "activated")
    private boolean activated;
    // 생성자, getter, setter, 기타 메서드




    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<AuthorityEntity> authorities;
}



