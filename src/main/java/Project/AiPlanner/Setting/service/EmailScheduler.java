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
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("일정 알림\n\n");

        for (DayPlanAlarmDto dayPlan : upcomingDayPlans) {
            textBuilder.append("일정: ");
            textBuilder.append(dayPlan.getPlanName());
            textBuilder.append("\n");
            textBuilder.append("시작 시간: ");
            textBuilder.append(dayPlan.getStart()); // 시작 시간 추가
            textBuilder.append("\n");
            textBuilder.append("마지막 시간: ");
            textBuilder.append(dayPlan.getEnd()); // 마지막 시간 추가
            textBuilder.append("\n\n");

            String to = dayPlan.getEmail(); // 수신자 이메일 주소
            String subject = "일정 알림";
            String text = textBuilder.toString();
            emailService.sendSimpleMessage(to, subject, text);
        }
    }
}