package Project.AiPlanner.User.repository;

import Project.AiPlanner.User.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    // 추가적인 메서드를 정의할 수 있음
    List<UserEntity> findByUserId(String userId);
    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByUserName(String username);
}