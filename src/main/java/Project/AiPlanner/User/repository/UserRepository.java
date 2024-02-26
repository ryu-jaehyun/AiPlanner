package Project.AiPlanner.User.repository;

import Project.AiPlanner.User.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT u.userName FROM UserEntity u WHERE u.userId = :userId")
    String findUserNameByUserId(@Param("userId") String userId);


    Optional<UserEntity> findOneWithAuthoritiesByUserId(String userId);

    Optional<UserEntity> findUserIdByPhoneNum(String phoneNum);

    Optional<UserEntity> findUserPasswordByUserIdAndPhoneNum(String userId, String phoneNum);

    Optional<UserEntity> findUserByUserIdAndPhoneNum(String userId, String phoneNum);
}