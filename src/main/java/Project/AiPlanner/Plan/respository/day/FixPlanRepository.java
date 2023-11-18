package Project.AiPlanner.Plan.respository.day;

import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FixPlanRepository extends CrudRepository<FixPlanEntity, Integer> {
    // 필요한 추가적인 메소드를 선언할 수 있음
    List<FixPlanEntity> findByUserId(String userId);
}