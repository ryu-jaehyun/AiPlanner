package Project.AiPlanner.Plan.controller.united;


import Project.AiPlanner.Plan.Dto.UpdateSuccessResponseDto;
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
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<UpdateSuccessResponseDto> updateSuccess(@RequestBody DaySuccessDto daySuccessDto) {
        String userId = SecurityUtil.getCurrentUserId();
        String userName = userRepository.findUserNameByUserId(userId);
        log.info("사용자아이디 ={}",userId);

        String result = dayPlanService.updateSuccessAndGetAverage(daySuccessDto);
        String message;

        // 결과에 따라 다른 메시지 생성
        double resultValue = Double.parseDouble(result.replaceAll("[^0-9.]", ""));
        if (resultValue < 50) {
            message = "달성률 저조, 분발하세요!";
        } else if (resultValue < 80) {
            message = "달성률 좋음. 조금만 더 화이팅!";
        } else {
            message = "달성률최고! 꾸준히 유지하세요!";
        }

        UpdateSuccessResponseDto response = new UpdateSuccessResponseDto();
        response.setMessage(message);
        response.setResult(result);

        return ResponseEntity.ok(response);
    }




    @PatchMapping("/month")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<UpdateSuccessResponseDto> updateSuccess(@RequestBody MonthSuccessDto monthSuccessDto) {
        String userId = SecurityUtil.getCurrentUserId();
        String userName = userRepository.findUserNameByUserId(userId);
        log.info("사용자아이디 ={}",userId);

        String result = monthPlanService.updateSuccessAndGetAverage(monthSuccessDto);
        String message;

        // 결과에 따라 다른 메시지 생성
        double resultValue = Double.parseDouble(result.replaceAll("[^0-9.]", ""));
        if (resultValue < 50) {
            message = "달성률 저조, 분발하세요!";
        } else if (resultValue < 80) {
            message = "달성률 좋음. 조금만 더 화이팅!";
        } else {
            message = "달성률최고! 꾸준히 유지하세요!";
        }

        UpdateSuccessResponseDto response = new UpdateSuccessResponseDto();
        response.setMessage(message);
        response.setResult(result);

        return ResponseEntity.ok(response);
    }
}
