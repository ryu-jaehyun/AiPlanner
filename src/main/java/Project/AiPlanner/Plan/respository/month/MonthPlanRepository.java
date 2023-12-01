package Project.AiPlanner.Plan.respository.month;

import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MonthPlanRepository extends CrudRepository<MonthPlanEntity, Integer> {
    // 필요한 추가적인 메소드를 선언할 수 있음
    List<MonthPlanEntity> findByUserId(String userId);
    void deleteByUserIdAndPlanId(String userId, Integer planId);

    Optional<MonthPlanEntity> findByPlanIdAndUserId(Integer planId, String userId);

    double countByUserId(String userId);
    @Query("SELECT SUM(dp.success) FROM MonthPlanEntity dp WHERE dp.userId = :userId")
    Integer sumSuccessByUserId(@Param("userId") String userId);
}