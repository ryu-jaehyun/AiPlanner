package Project.AiPlanner.Plan.controller.day;


import Project.AiPlanner.Plan.respository.day.FixPlanRepository;
import Project.AiPlanner.Plan.service.day.FixPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/plan/day")
public class FixPlanController {

    @Autowired
    private FixPlanService fixPlanService;

    @Autowired
    private FixPlanRepository fixPlanRepository;
}
