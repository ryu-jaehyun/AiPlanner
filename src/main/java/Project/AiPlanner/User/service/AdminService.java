package Project.AiPlanner.User.service;

import Project.AiPlanner.User.Dto.AdminRegistrationDto;
import Project.AiPlanner.User.entity.AuthorityEntity;
import Project.AiPlanner.User.entity.UserEntity;
import Project.AiPlanner.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void registerAdmin(AdminRegistrationDto adminDto) {
        AuthorityEntity authority = AuthorityEntity.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        UserEntity admin = UserEntity.builder()
                .userId(adminDto.getUserId())
                .userPw(passwordEncoder.encode(adminDto.getUserPw()))
                .userName(adminDto.getRealName())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(admin);
    }
}
