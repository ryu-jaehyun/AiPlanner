package Project.AiPlanner.Plan.service.day;

import Project.AiPlanner.Plan.Dto.day.FixPlanDto;
import Project.AiPlanner.Plan.Dto.day.FlowPlanDto;
import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import Project.AiPlanner.Plan.entity.day.FlowPlanEntity;
import Project.AiPlanner.Plan.respository.day.FixPlanRepository;
import Project.AiPlanner.Plan.respository.day.FlowPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayPlanService {
    private final FixPlanRepository fixPlanRepository;
    private final FlowPlanRepository flowPlanRepository;
    private final ModelMapper modelMapper;


    public boolean saveFixPlan(FixPlanDto fixPlanDto,String id) {

        try {
            FixPlanEntity fixPlanEntity = FixPlanEntity.builder()
                    .planName(fixPlanDto.getPlanName())
                    .userId(id)
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
    public List<FixPlanDto> convertToFixDtoList(List<FixPlanEntity> entities) {
        return entities.stream()
                .map(this::convertToFixDto)
                .collect(Collectors.toList());
    }

    public FixPlanDto convertToFixDto(FixPlanEntity entity) {
        return modelMapper.map(entity, FixPlanDto.class);
        // Assuming ModelMapper is used for mapping between entity and DTO
    }
    public List<FlowPlanDto> convertToFlowDtoList(List<FlowPlanEntity> entities) {
        return entities.stream()
                .map(this::convertToFlowDto)
                .collect(Collectors.toList());
    }

    public FlowPlanDto convertToFlowDto(FlowPlanEntity entity) {
        return modelMapper.map(entity, FlowPlanDto.class);
        // Assuming ModelMapper is used for mapping between entity and DTO
    }

    public boolean saveFlowPlan(FlowPlanDto flowPlanDto, String id) {

        try {
            FlowPlanEntity flowPlanEntity = FlowPlanEntity.builder()
                    .planName(flowPlanDto.getPlanName())
                    .userId(id)
                    .planType(flowPlanDto.getPlanType())
                    .start(flowPlanDto.getStart())
                    .end(flowPlanDto.getEnd())
                    .build();

            FlowPlanEntity savedEntity = flowPlanRepository.save(flowPlanEntity);

            return savedEntity != null; // 저장이 성공했으면 true, 실패했으면 false 반환
        } catch (Exception e) {
            // 예외 발생 시 로깅 또는 다른 처리를 추가할 수 있습니다.
            log.error("FlowPlan save failed : {}", e);
            return false; // 예외 발생 시 false 반환
        }
    }

    public FixPlanEntity getFixPlanById(int id) {
        return fixPlanRepository.findById(id).orElse(null);
    }

    // 필요한 다른 비즈니스 로직을 구현할 수 있음
}