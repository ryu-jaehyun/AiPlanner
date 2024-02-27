package Project.AiPlanner.Setting.service;

import Project.AiPlanner.Plan.service.day.DayPlanService;
import Project.AiPlanner.Setting.dto.DayPlanAlarmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private DayPlanService dayPlanService;

    @Scheduled(cron = "0 0 23 * * ?") // 매일 오후 11시에 실행
    public void sendEmailForUpcomingDayPlans() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = now.minusDays(1);

        List<DayPlanAlarmDto> upcomingDayPlans = dayPlanService.getUpcomingDayPlans(oneDayAgo);

        for (DayPlanAlarmDto dayPlan : upcomingDayPlans) {
            String to = dayPlan.getEmail(); // 수신자 이메일 주소
            String subject = "일정 알림";
            String text = "일정 '" + dayPlan.getPlanName() + "'의 시작 시간이 하루 남았습니다.";
            emailService.sendSimpleMessage(to, subject, text);
        }
    }
}