package Project.AiPlanner.Plan.controller.day;


import Project.AiPlanner.Plan.Dto.day.DayPlanDeleteDto;
import Project.AiPlanner.Plan.Dto.day.DayPlanDto;
import Project.AiPlanner.Plan.Dto.day.DayPlanUpdateDto;
import Project.AiPlanner.Plan.Dto.day.DayTypeColorDto;
import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.respository.day.DayPlanRepository;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import Project.AiPlanner.Plan.service.day.DayPlanService;
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
@RequestMapping("/plan/day")
public class dayPlanController {

    private final DayPlanService dayPlanService;
    private final DayPlanRepository dayPlanRepository;
    private final UserService userService;


    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> createDayPlan(@Valid @RequestBody DayPlanDto dayPlanDto) {

        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);




        if (dayPlanService.
                savePlan(dayPlanDto, userId)) {
            List<DayPlanEntity> dayPlanEntities = dayPlanRepository.findByUserId(userId);
            return ResponseEntity.ok(dayPlanEntities); // Returning entities directly
        } else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> deleteDayPlan(@Valid @RequestBody DayPlanDeleteDto dayPlanDeleteDto) {


        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);
        Integer planId = dayPlanDeleteDto.getPlanId();

        log.info("planid ={}", planId);

        if(dayPlanService.deleteDayPlan(planId, userId)){
            List<DayPlanEntity> dayPlanEntities = dayPlanRepository.findByUserId(userId);
            return ResponseEntity.ok(dayPlanEntities); // Returning entities directly
        }
        else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> updateDayPlan(@Valid @RequestBody DayPlanUpdateDto dayPlanUpdateDto){
        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);
        Integer planId = dayPlanUpdateDto.getPlanId();
        if(dayPlanService.updateDayPlan(planId, userId,dayPlanUpdateDto)){
            List<DayPlanEntity> dayPlanEntities = dayPlanRepository.findByUserId(userId);
            return ResponseEntity.ok(dayPlanEntities); // Returning entities directly
        }
        else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }





    }
    @GetMapping("/type")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> getUniquePlanTypesAndColors(){
        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);
        List<DayTypeColorDto> uniqueTypesColors = dayPlanService.getUniquePlanTypesAndColors(userId);
        if(uniqueTypesColors == null){
            return new ResponseEntity<>("일정타입을 등록해주세요",HttpStatus.OK);
        }
        return ResponseEntity.ok(uniqueTypesColors);
    }

    }

