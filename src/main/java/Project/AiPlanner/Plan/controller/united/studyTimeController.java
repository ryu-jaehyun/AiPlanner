package Project.AiPlanner.Plan.controller.united;


import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.respository.day.DayPlanRepository;
import Project.AiPlanner.Util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class studyTimeController {

    private final DayPlanRepository dayPlanRepository;


    @GetMapping("/totalStudyTime")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<List<Long>> getTotalStudyTime() {
        String userId = SecurityUtil.getCurrentUserId();
        // 공부 타입이면서 success가 1인 일정들을 가져옵니다.
        List<DayPlanEntity> studyPlans = dayPlanRepository.findByUserIdAndPlanTypeAndSuccess(userId, "공부", 1);

        Duration totalDuration = Duration.ZERO;

        for (DayPlanEntity plan : studyPlans) {
            LocalDateTime start = plan.getStart();
            LocalDateTime end = plan.getEnd();

            Duration duration = Duration.between(start, end);
            totalDuration = totalDuration.plus(duration);
        }

        long hours = totalDuration.toHours();
        long minutes = totalDuration.minusHours(hours).toMinutes();

        List<Long> result = new ArrayList<>();
        result.add(hours);
        result.add(minutes);

        return ResponseEntity.ok(result);
    }

}
