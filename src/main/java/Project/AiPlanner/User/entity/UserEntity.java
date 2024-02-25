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



    //초기 관리자와 달리 새로운 관리자를 추가할 때 초기 관리자에게 승인을 받고 승인을 받으면 관리자 계정을 생성한다.
    //이후 초기 관리자가 해당 계정에 관리자 권한을 부여한다.
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<AuthorityEntity> authorities;
}



