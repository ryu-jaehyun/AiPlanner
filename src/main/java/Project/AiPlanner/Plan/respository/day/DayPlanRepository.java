package Project.AiPlanner.Plan.respository.day;

import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DayPlanRepository extends CrudRepository<DayPlanEntity, Integer> {
    // 필요한 추가적인 메소드를 선언할 수 있음
    List<DayPlanEntity> findByUserId(String userId);
    void deleteByUserIdAndPlanId(String userId, Integer planId);

    Optional<DayPlanEntity> findByPlanIdAndUserId(Integer planId, String userId);



    Integer countByUserId(String userId);
    @Query("SELECT SUM(dp.success) FROM DayPlanEntity dp WHERE dp.userId = :userId")
    Integer sumSuccessByUserId(@Param("userId") String userId);
}