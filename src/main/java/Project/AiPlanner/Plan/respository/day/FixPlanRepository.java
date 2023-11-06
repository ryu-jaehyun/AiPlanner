package Project.AiPlanner.Plan.respository.day;

import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import org.springframework.data.repository.CrudRepository;

public interface FixPlanRepository extends CrudRepository<FixPlanEntity, Integer> {
    // 필요한 추가적인 메소드를 선언할 수 있음
}