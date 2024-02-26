package Project.AiPlanner.Setting.controller;

import Project.AiPlanner.Setting.dto.DayPlanAlarmDto;
import Project.AiPlanner.Setting.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/setting")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public String sendEmail(@RequestBody DayPlanAlarmDto request) {
        String to = request.getEmail();
        String subject = request.getPlanName();
        String body = "일정 종류: " + request.getPlanType() + "\n" +
                "시작 시간: " + request.getStart() + "\n" +
                "종료 시간: " + request.getEnd() + "\n" +
                "일정 구분: " + request.getPlan();

        emailService.sendSimpleMessage(to, subject, body);

        return "Email sent successfully!";
    }
}
