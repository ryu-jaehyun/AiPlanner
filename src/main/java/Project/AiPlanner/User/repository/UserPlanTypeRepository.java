package Project.AiPlanner.User.repository;

import Project.AiPlanner.User.entity.UserPlanTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPlanTypeRepository extends JpaRepository<UserPlanTypeEntity, Integer> {
    List<UserPlanTypeEntity> findByUserId(String userId);
}
