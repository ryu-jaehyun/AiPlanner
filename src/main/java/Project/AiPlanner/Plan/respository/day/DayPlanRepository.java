package Project.AiPlanner.Plan.respository.day;

import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // 겹치는 "고정"인 일정을 찾는 메서드
    @Query("SELECT dp FROM DayPlanEntity dp " +
            "WHERE dp.userId = :userId " +
            "AND dp.plan = '고정' " +
            "AND (:newStart < dp.end AND :newEnd > dp.start)")
    List<DayPlanEntity> findOverlappingFixedPlans(
            @Param("userId") String userId,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );
    @Query("SELECT dp FROM DayPlanEntity dp WHERE dp.userId = :userId AND dp.plan = '고정'")
    List<DayPlanEntity> findFixedPlansByUserId(String userId);

    List<DayPlanEntity> findByUserIdAndPlanType(String userId, String planType);

    @Query("SELECT d FROM DayPlanEntity d WHERE d.userId = :userId AND DATE(d.start) = :date AND d.plan = :plan")
    List<DayPlanEntity> findByUserIdAndStartAndPlan(String userId, LocalDate date, String plan);
}