package Project.AiPlanner.Function.controller;

import Project.AiPlanner.Function.service.AuthService;
import Project.AiPlanner.Function.service.CustomMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmController {

    @Autowired
    AuthService authService;

    @Autowired
    CustomMessageService customMessageService;

    @GetMapping("/kakao")
    public String serviceStart(String code) {
        if(authService.getKakaoAuthToken(code)) {
            customMessageService.sendMyMessage();
            return "메시지 전송 성공";
        }else {
            return "토큰발급 실패";
        }
    }
}