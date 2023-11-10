package Project.AiPlanner.Plan.controller.day;


import Project.AiPlanner.Plan.Dto.day.FixPlanDto;
import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import Project.AiPlanner.Plan.respository.day.FixPlanRepository;
import Project.AiPlanner.Plan.service.day.FixPlanService;
import Project.AiPlanner.User.Dto.UserFormDto;
import Project.AiPlanner.Util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/plan/day")
public class FixPlanController {

    private final FixPlanService fixPlanService;
    private final FixPlanRepository fixPlanRepository;

    @Autowired
    public FixPlanController(FixPlanService fixPlanService, FixPlanRepository fixPlanRepository) {
        this.fixPlanService = fixPlanService;
        this.fixPlanRepository = fixPlanRepository;
    }

    @PostMapping("/add")
   // @CrossOrigin(origins = "*")
    public ResponseEntity<String> createDayPlanFix(@Valid @RequestBody FixPlanDto fixPlanDto) {

        String currentUserId = SecurityUtil.getCurrentUserId();
        fixPlanDto.setUserId(currentUserId);


        if(fixPlanService.saveFixPlan(fixPlanDto)!=false)
            return new ResponseEntity<>("일정등록 완료!",HttpStatus.OK);
        else
            return new ResponseEntity<>("요청이 잘못되었습니다. 다시 일정을 확인해주세요", HttpStatus.BAD_REQUEST);


    }

}

