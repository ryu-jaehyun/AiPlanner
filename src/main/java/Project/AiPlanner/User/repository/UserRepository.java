package Project.AiPlanner.User.repository;

import Project.AiPlanner.User.entity.UserEntity;
import org.modelmapper.Converters;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    // 추가적인 메서드를 정의할 수 있음
    List<UserEntity> findByUserId(String userId);
    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByUserName(String username);
    // 새로운 메서드 추가
    Optional<UserEntity> findByUserName(String userName);
    Optional<String> findUserIdByUserName(String userName);
    String findUserNameByPhoneNum(String phoneNum);

    Optional<UserEntity> findOneWithAuthoritiesByUserId(String userId);

    String findUserIdByPhoneNum(String phoneNum);

    Optional<String> findUserPasswordByUserIdAndPhoneNum(String userId, String phoneNum);
}