package Project.AiPlanner.User.service;

import Project.AiPlanner.User.Dto.UserFormDto;
import Project.AiPlanner.User.entity.AuthorityEntity;
import Project.AiPlanner.User.entity.UserEntity;
import Project.AiPlanner.User.repository.UserRepository;
import Project.AiPlanner.Util.SecurityUtil;
import Project.AiPlanner.exception.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String findByUserName(String userName) {
        // UserRepository에서 findByUserName을 이용하여 UserEntity를 가져옴
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(userName);

        // 가져온 UserEntity가 존재하면 userId를 반환, 그렇지 않으면 예외 처리 또는 기본값 등을 적용
        return userEntityOptional.map(UserEntity::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found for userName: " + userName));
    }

    //signup()을 통해 가입한 회원은 USER ROLE을 가지고 있다.
    @Transactional
    public UserFormDto signup(UserFormDto userDto) {


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

    /**
     * 아이디 회원 중복 검증 메서드
     **/
    public boolean isUserIdUnique(String userId) {
        // 아이디로 사용자 조회
        List<UserEntity> existingUsers = userRepository.findByUserId(userId);

        // 결과 리스트가 비어있으면 중복되지 않은 아이디
        // 결과 리스트에 사용자가 포함되어 있다면 중복 아이디
        return existingUsers.isEmpty();
    }

    public String updateAndReturnTempPassword(String userId, String phoneNum) {
        // 임시 비밀번호 생성 (랜덤한 문자열)
        String tempPassword = generateRandomPassword(10);

        // 임시 비밀번호를 DB에 업데이트
        Optional<UserEntity> userOptional = userRepository.findUserByUserIdAndPhoneNum(userId, phoneNum);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // 비밀번호 업데이트
            user.setUserPw(passwordEncoder.encode(tempPassword));
            userRepository.save(user);

            // 임시 비밀번호 반환
            return tempPassword;
        } else {
            throw new RuntimeException("User not found for the given userId and phoneNum.");
        }
    }

    private String generateRandomPassword(int length) {
        // 임시 비밀번호를 랜덤하게 생성하는 코드 (예: 알파벳 대소문자, 숫자 조합)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}

