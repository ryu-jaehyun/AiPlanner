package Project.AiPlanner.Plan.controller.united;


import Project.AiPlanner.Plan.Dto.day.DaySuccessDto;
import Project.AiPlanner.Plan.Dto.month.MonthSuccessDto;
import Project.AiPlanner.Plan.respository.day.DayPlanRepository;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import Project.AiPlanner.Plan.service.day.DayPlanService;
import Project.AiPlanner.Plan.service.month.MonthPlanService;
import Project.AiPlanner.User.repository.UserRepository;
import Project.AiPlanner.Util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan/success")
public class successController {

    private final DayPlanService dayPlanService;
    private final MonthPlanService monthPlanService;
    private  final UserRepository userRepository;
    @PatchMapping("/day")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateSuccess(@RequestBody DaySuccessDto daySuccessDto) {
        String userId = SecurityUtil.getCurrentUserId();
        String userName = userRepository.findUserNameByUserId(userId);
        log.info("사용자아이디 ={}",userId);

        String result = dayPlanService.updateSuccessAndGetAverage(daySuccessDto);
        return ResponseEntity.ok(userName + "의 달성률 : " +result);


    }

    @PatchMapping("/month")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateSuccess(@RequestBody MonthSuccessDto monthSuccessDto) {
        String userId = SecurityUtil.getCurrentUserId();
        String userName = userRepository.findUserNameByUserId(userId);
        log.info("사용자아이디 ={}",userId);

        String result = monthPlanService.updateSuccessAndGetAverage(monthSuccessDto);
        return ResponseEntity.ok(userName + "의 달성률 : " +result);


    }
}
