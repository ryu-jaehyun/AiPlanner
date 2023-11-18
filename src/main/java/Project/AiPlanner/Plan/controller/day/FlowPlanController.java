package Project.AiPlanner.Plan.controller.day;

import Project.AiPlanner.Plan.Dto.day.FixPlanDto;
import Project.AiPlanner.Plan.Dto.day.FlowPlanDto;
import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import Project.AiPlanner.Plan.entity.day.FlowPlanEntity;
import Project.AiPlanner.Plan.respository.day.FixPlanRepository;
import Project.AiPlanner.Plan.respository.day.FlowPlanRepository;
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
@RequestMapping("/plan/day/flow")
public class FlowPlanController {

    private final DayPlanService flowPlanService;
    private final FlowPlanRepository flowPlanRepository;
    private final UserService userService;

    @Autowired
    public FlowPlanController(DayPlanService flowPlanService, FlowPlanRepository flowPlanRepository, UserService userService) {
        this.flowPlanService = flowPlanService;
        this.flowPlanRepository = flowPlanRepository;
        this.userService = userService;
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> createDayPlanFlow(@Valid @RequestBody FlowPlanDto flowPlanDto) {

        String currentUserName = SecurityUtil.getCurrentUserId();

        log.info("사용자이름 ={}",currentUserName);

        String userId = userService.findByUserName(currentUserName);


        if (flowPlanService.saveFlowPlan(flowPlanDto, userId)) {
            List<FlowPlanEntity> flowPlanEntities = flowPlanRepository.findByUserId(userId);
            List<FlowPlanDto> flowPlanDtos = flowPlanService.convertToFlowDtoList(flowPlanEntities);
            return ResponseEntity.ok(flowPlanDtos);}
        else
            return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);


    }

}