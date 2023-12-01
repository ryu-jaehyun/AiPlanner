package Project.AiPlanner.Plan.controller.month;


import Project.AiPlanner.Plan.Dto.day.DayPlanDeleteDto;
import Project.AiPlanner.Plan.Dto.day.DayPlanUpdateDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanDeleteDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanUpdateDto;
import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import Project.AiPlanner.Plan.service.month.MonthPlanService;
import Project.AiPlanner.User.service.UserService;
import Project.AiPlanner.Util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan/month")
public class MonthPlanController {

    private final MonthPlanRepository monthPlanRepository;
    private final MonthPlanService monthPlanService;
    private final UserService userService;


    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> createMonthPlan(@Valid @RequestBody MonthPlanDto monthPlanDto) {

        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);


        if (monthPlanService.saveMonthPlan(monthPlanDto, userId)) {
            List<MonthPlanEntity> monthPlanEntities = monthPlanRepository.findByUserId(userId);
            return ResponseEntity.ok(monthPlanEntities);} // Returning entities directly
        else
            return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);


    }

    @PostMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> deleteMonthPlan(@Valid @RequestBody MonthPlanDeleteDto monthPlanDeleteDto) {


        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);
        Integer planId = monthPlanDeleteDto.getPlanId();

        log.info("planid ={}", planId);

        if(monthPlanService.deleteMonthPlan(planId, userId)){
            List<MonthPlanEntity> monthPlanEntities = monthPlanRepository.findByUserId(userId);
            return ResponseEntity.ok(monthPlanEntities); // Returning entities directly
        }
        else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> updateMonthPlan(@RequestBody MonthPlanUpdateDto monthPlanUpdateDto){
        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);
        Integer planId = monthPlanUpdateDto.getPlanId();
        if(monthPlanService.updateMonthPlan(planId, userId,monthPlanUpdateDto)){
            List<MonthPlanEntity> monthPlanEntities = monthPlanRepository.findByUserId(userId);
            return ResponseEntity.ok(monthPlanEntities); // Returning entities directly
        }
        else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }





    }

}
