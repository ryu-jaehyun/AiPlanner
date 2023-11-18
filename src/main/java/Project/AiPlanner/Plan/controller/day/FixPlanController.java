package Project.AiPlanner.Plan.controller.day;


import Project.AiPlanner.Plan.Dto.day.FixPlanDto;
import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import Project.AiPlanner.Plan.respository.day.FixPlanRepository;
import Project.AiPlanner.Plan.service.day.DayPlanService;
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
@RequestMapping("/plan/day/fix")
public class FixPlanController {

    private final DayPlanService fixPlanService;
    private final FixPlanRepository fixPlanRepository;
    private final UserService userService;

    @Autowired
    public FixPlanController(DayPlanService fixPlanService, FixPlanRepository fixPlanRepository, UserService userService) {
        this.fixPlanService = fixPlanService;
        this.fixPlanRepository = fixPlanRepository;
        this.userService = userService;
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> createDayPlanFix(@Valid @RequestBody FixPlanDto fixPlanDto) {

        String currentUserName = SecurityUtil.getCurrentUserId();

        log.info("사용자이름 ={}",currentUserName);

        String userId = userService.findByUserName(currentUserName);


        if (fixPlanService.saveFixPlan(fixPlanDto, userId)) {
            List<FixPlanEntity> fixPlanEntities = fixPlanRepository.findByUserId(userId);
            List<FixPlanDto> fixPlanDtos = fixPlanService.convertToFixDtoList(fixPlanEntities);
            return ResponseEntity.ok(fixPlanDtos);
        } else {
            return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }

}

