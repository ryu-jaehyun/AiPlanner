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
import Project.AiPlanner.User.Dto.UserPlanTypeColorDto;
import Project.AiPlanner.User.service.UserService;
import Project.AiPlanner.Util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<String> createDayPlan(@Valid @RequestBody List<DayPlanDto> dayPlanDtoList) {

        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);



        boolean allSaved = true;
        for (DayPlanDto dayPlanDto : dayPlanDtoList) {
            LocalDateTime newStart = dayPlanDto.getStart();
            LocalDateTime newEnd = dayPlanDto.getEnd();

            // Query to find overlapping plans in the database with "plan" column as "고정"
            List<DayPlanEntity> overlappingPlans = dayPlanRepository.findOverlappingFixedPlans(userId, newStart, newEnd);

            // Delete overlapping plans from the database with "plan" column as "고정"
            for (DayPlanEntity overlappingPlan : overlappingPlans) {
                if (overlappingPlan.getPlan().equals("고정")) {
                    dayPlanRepository.delete(overlappingPlan);
                }
            }

            // Save the new plan
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



    @PostMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> deleteDayPlan(@Valid @RequestBody DayPlanDeleteDto dayPlanDeleteDto) {


        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);
        Integer planId = dayPlanDeleteDto.getPlanId();

        log.info("planid ={}", planId);

        if(dayPlanService.deleteDayPlan(planId, userId)){

            return new ResponseEntity<>("일일일정 삭제 성공!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateDayPlan(@Valid @RequestBody DayPlanUpdateDto dayPlanUpdateDto){
        String userId = SecurityUtil.getCurrentUserId();

        log.info("사용자아이디 ={}",userId);
        Integer planId = dayPlanUpdateDto.getPlanId();
        if(dayPlanService.updateDayPlan(planId, userId,dayPlanUpdateDto)){

            return new ResponseEntity<>("일일일정 수정 성공!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 삭제할 일정을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }}
    @GetMapping("/get")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> getDayPlan(){
            String userId = SecurityUtil.getCurrentUserId();
        List<DayPlanEntity> dayPlanEntities = dayPlanRepository.findByUserId(userId);
        return ResponseEntity.ok(dayPlanEntities);
        }





   // @GetMapping("/type")
    //@CrossOrigin(origins = "http://localhost:3000")
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

