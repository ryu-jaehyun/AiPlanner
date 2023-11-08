package Project.AiPlanner.User.service;

import Project.AiPlanner.User.Dto.UserFormDto;
import Project.AiPlanner.User.entity.AuthorityEntity;
import Project.AiPlanner.User.entity.UserEntity;
import Project.AiPlanner.User.repository.UserRepository;
import Project.AiPlanner.Util.SecurityUtil;
import Project.AiPlanner.exception.DuplicateMemberException;
import Project.AiPlanner.exception.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    //signup()을 통해 가입한 회원은 USER ROLE을 가지고 있다.
    @Transactional
    public UserFormDto signup(UserFormDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUserName(userDto.getUserName()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        AuthorityEntity authority = AuthorityEntity.builder()
                .authorityName("ROLE_USER")
                .build();

        UserEntity user = UserEntity.builder()
                .userName(userDto.getUserName())
                .userId(userDto.getUserId())
                .birth(userDto.getBirth())
                .sex(userDto.getSex())
                .student(userDto.getStudent())
                .phoneNum(userDto.getPhoneNum())
                .userPw(passwordEncoder.encode(userDto.getUserPw()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserFormDto.from(userRepository.save(user));
    }
    @Transactional(readOnly = true)
    public UserFormDto getUserWithAuthorities(String username) {
        return UserFormDto.from(userRepository.findOneWithAuthoritiesByUserName(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserFormDto getMyUserWithAuthorities() {
        return UserFormDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUserName)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

   /** 아이디 회원 중복 검증 메서드 **/
    public boolean isUserIdUnique(String userId) {
        // 아이디로 사용자 조회
        List<UserEntity> existingUsers = userRepository.findByUserId(userId);

        // 결과 리스트가 비어있으면 중복되지 않은 아이디
        // 결과 리스트에 사용자가 포함되어 있다면 중복 아이디
        return existingUsers.isEmpty();
    }
}