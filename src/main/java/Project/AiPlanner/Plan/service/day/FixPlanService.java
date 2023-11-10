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

        try {
            FixPlanEntity fixPlanEntity = FixPlanEntity.builder()
                    .planName(fixPlanDto.getPlanName())
                    .userId(fixPlanDto.getUserId())
                    .planType(fixPlanDto.getPlanType())
                    .start(fixPlanDto.getStart())
                    .end(fixPlanDto.getEnd())
                    .build();

            FixPlanEntity savedEntity = fixPlanRepository.save(fixPlanEntity);

            return savedEntity != null; // 저장이 성공했으면 true, 실패했으면 false 반환
        } catch (Exception e) {
            // 예외 발생 시 로깅 또는 다른 처리를 추가할 수 있습니다.
            log.error("FixPlan save failed : {}", e);
            return false; // 예외 발생 시 false 반환
        }
    }

    public FixPlanEntity getFixPlanById(int id) {
        return fixPlanRepository.findById(id).orElse(null);
    }

    // 필요한 다른 비즈니스 로직을 구현할 수 있음
}