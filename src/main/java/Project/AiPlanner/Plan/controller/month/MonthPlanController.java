package Project.AiPlanner.Plan.controller.month;


import Project.AiPlanner.Plan.Dto.day.FlowPlanDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanDto;
import Project.AiPlanner.Plan.entity.day.FlowPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import Project.AiPlanner.Plan.service.month.MonthPlanService;
import Project.AiPlanner.User.service.UserService;
import Project.AiPlanner.Util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plan/month")
public class MonthPlanController {

    private final MonthPlanRepository monthPlanRepository;
    private final MonthPlanService monthPlanService;
    private final UserService userService;

    @Autowired
    public MonthPlanController(MonthPlanRepository monthPlanRepository, MonthPlanService monthPlanService, UserService userService) {
        this.monthPlanRepository = monthPlanRepository;
        this.monthPlanService = monthPlanService;
        this.userService = userService;
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> createMonthPlan(@Valid @RequestBody MonthPlanDto monthPlanDto) {

        String currentUserName = SecurityUtil.getCurrentUserId();

        log.info("사용자이름 ={}",currentUserName);

        String userId = userService.findByUserName(currentUserName);


        if (monthPlanService.saveMonthPlan(monthPlanDto, userId)) {
            List<MonthPlanEntity> monthPlanEntities = monthPlanRepository.findByUserId(userId);
            List<MonthPlanDto> monthPlanDtos = monthPlanService.convertToMonthDtoList(monthPlanEntities);
            return ResponseEntity.ok(monthPlanDtos);}
        else
            return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);


    }

}
