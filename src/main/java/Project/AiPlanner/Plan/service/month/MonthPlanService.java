package Project.AiPlanner.Plan.service.month;


import Project.AiPlanner.Plan.Dto.day.DayPlanUpdateDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanUpdateDto;
import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    @Transactional   //일반적으로 DB 데이터를 등록/수정/삭제하는 Service메서드는 @Transactional을 필수적으로 가져간다.
    public boolean deleteMonthPlan(Integer planId,String userId) {
        try {
            log.info("planid={}",planId);
            monthPlanRepository.deleteByUserIdAndPlanId(userId, planId);
            return true;
        } catch (Exception e) {

            return false;
        }
    }
    @Transactional
    public boolean updateMonthPlan(Integer planId,String userId, MonthPlanUpdateDto monthPlanUpdateDto) {
        try {
            Optional<MonthPlanEntity> optionalMonthPlan = monthPlanRepository.findByPlanIdAndUserId(planId,userId);

            if (optionalMonthPlan.isPresent()) {
                MonthPlanEntity monthPlan = optionalMonthPlan.get();
                if (!monthPlan.getUserId().equals(userId)) {
                    return false; // User does not have permission to update this plan
                }
                // Check and update fields if they are not null in the DTO
                if (monthPlanUpdateDto.getPlanName() != null) {
                    monthPlan.setPlanName(monthPlanUpdateDto.getPlanName());
                }
                if (monthPlanUpdateDto.getPlanType() != null) {
                    monthPlan.setPlanType(monthPlanUpdateDto.getPlanType());
                }
                if (monthPlanUpdateDto.getStart() != null) {
                    monthPlan.setStart(monthPlanUpdateDto.getStart());
                }
                if (monthPlanUpdateDto.getEnd() != null) {
                    monthPlan.setEnd(monthPlanUpdateDto.getEnd());
                }


                // Ensure the planId matches before saving changes
                if (monthPlan.getPlanId() == monthPlanUpdateDto.getPlanId()) {
                    monthPlanRepository.save(monthPlan);
                    return true;
                } else {
                    return false; // Provided planId does not match the entity's planId
                }
            } else {
                return false; // Plan with given ID not found
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception
            return false; // Update operation failed
        }
    }
}
