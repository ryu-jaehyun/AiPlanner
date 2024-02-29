package Project.AiPlanner.Plan.controller.day;


import Project.AiPlanner.Plan.Dto.day.DayPlanDeleteDto;
import Project.AiPlanner.Plan.Dto.day.DayPlanDto;
import Project.AiPlanner.Plan.Dto.day.DayPlanUpdateDto;
import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.respository.day.DayPlanRepository;
import Project.AiPlanner.Plan.service.day.DayPlanService;
import Project.AiPlanner.User.service.UserService;
import Project.AiPlanner.Util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan/day")
public class dayPlanController {

    private final DayPlanService dayPlanService;
    private final DayPlanRepository dayPlanRepository;
    private final UserService userService;


    @PostMapping("/add")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<String> createDayPlan(@Valid @RequestBody List<DayPlanDto> dayPlanDtoList) {

        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}", userId);


        if (dayPlanDtoList.size() == 1) {
            // 배열에 데이터가 1개인 경우, 삭제하지 않고 그대로 저장
            if (dayPlanService.savePlan(dayPlanDtoList.get(0), userId)) {
                return new ResponseEntity<>("일일일정 등록 성공!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);
            }
        }

        LocalDate commonDate = dayPlanDtoList.get(1).getStart().toLocalDate();
        List<DayPlanEntity> fluidPlans = dayPlanRepository.findByUserIdAndStartAndPlan(userId, commonDate, "유동");
        dayPlanRepository.deleteAll(fluidPlans);

        boolean allSaved = true;
        for (DayPlanDto dayPlanDto : dayPlanDtoList) {
            if (!dayPlanService.savePlan(dayPlanDto, userId)) {
                allSaved = false;
                break;
            }
        }

        if (allSaved) {
            return new ResponseEntity<>("일일일정 등록 성공!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<String> deleteDayPlan(@Valid @RequestBody DayPlanDeleteDto dayPlanDeleteDto) {


        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}", userId);
        Integer planId = dayPlanDeleteDto.getPlanId();

        log.info("planid ={}", planId);

        if (dayPlanService.deleteDayPlan(planId, userId)) {

            return new ResponseEntity<>("일일일정 삭제 성공!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<String> updateDayPlan(@Valid @RequestBody DayPlanUpdateDto dayPlanUpdateDto) {
        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}", userId);
        Integer planId = dayPlanUpdateDto.getPlanId();
        if (dayPlanService.updateDayPlan(planId, userId, dayPlanUpdateDto)) {

            return new ResponseEntity<>("일일일정 수정 성공!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<Object> getDayPlan() {
        String userId = SecurityUtil.getCurrentUserId();
        List<DayPlanEntity> dayPlanEntities = dayPlanService.getDayPlanByUserId(userId);
        return ResponseEntity.ok(dayPlanEntities);
    }


}

