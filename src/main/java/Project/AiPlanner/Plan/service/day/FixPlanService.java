package Project.AiPlanner.Plan.service.day;

import Project.AiPlanner.Plan.Dto.day.FixPlanDto;
import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import Project.AiPlanner.Plan.respository.day.FixPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FixPlanService {
    private final FixPlanRepository fixPlanRepository;



    public boolean saveFixPlan(FixPlanDto fixPlanDto) {

        FixPlanEntity fixPlanEntity = FixPlanEntity.builder().planName(fixPlanDto.getPlanName()).userId(fixPlanDto.getUserId())
                .planType(fixPlanDto.getPlanType()).start(fixPlanDto.getStart()).end(fixPlanDto.getEnd()).build();


        if(fixPlanEntity!=null){
            fixPlanRepository.save(fixPlanEntity);
            return true;
    }
    else return false;}

    public FixPlanEntity getFixPlanById(int id) {
        return fixPlanRepository.findById(id).orElse(null);
    }

    // 필요한 다른 비즈니스 로직을 구현할 수 있음
}