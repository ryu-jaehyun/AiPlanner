package Project.AiPlanner.Plan.respository.month;

import Project.AiPlanner.Plan.entity.day.FlowPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MonthPlanRepository extends CrudRepository<MonthPlanEntity, Integer> {
    // 필요한 추가적인 메소드를 선언할 수 있음
    List<MonthPlanEntity> findByUserId(String userId);

}