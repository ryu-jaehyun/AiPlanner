package Project.AiPlanner.Plan.service.month;


import Project.AiPlanner.Plan.Dto.day.FixPlanDto;
import Project.AiPlanner.Plan.Dto.day.FlowPlanDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanDto;
import Project.AiPlanner.Plan.entity.day.FixPlanEntity;
import Project.AiPlanner.Plan.entity.day.FlowPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MonthPlanService {


    private final MonthPlanRepository monthPlanRepository;
    private final ModelMapper modelMapper;

    public boolean saveMonthPlan(MonthPlanDto monthPlanDto, String id) {

        try {
            MonthPlanEntity monthPlanEntity = MonthPlanEntity.builder()
                    .planName(monthPlanDto.getPlanName())
                    .userId(id)
                    .planType(monthPlanDto.getPlanType())
                    .start(monthPlanDto.getStart())
                    .end(monthPlanDto.getEnd())
                    .build();

            MonthPlanEntity savedEntity = monthPlanRepository.save(monthPlanEntity);

            return savedEntity != null; // 저장이 성공했으면 true, 실패했으면 false 반환
        } catch (Exception e) {
            // 예외 발생 시 로깅 또는 다른 처리를 추가할 수 있습니다.
            log.error("MonthPlan save failed : {}", e);
            return false; // 예외 발생 시 false 반환
        }
    }
    public List<MonthPlanDto> convertToMonthDtoList(List<MonthPlanEntity> entities) {
        return entities.stream()
                .map(this::convertToMonthDto)
                .collect(Collectors.toList());
    }

    public MonthPlanDto convertToMonthDto(MonthPlanEntity entity) {
        return modelMapper.map(entity, MonthPlanDto.class);
        // Assuming ModelMapper is used for mapping between entity and DTO
    }
}
