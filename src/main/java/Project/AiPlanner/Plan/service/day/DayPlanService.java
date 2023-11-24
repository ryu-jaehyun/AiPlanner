package Project.AiPlanner.Plan.service.day;

import Project.AiPlanner.Plan.Dto.day.DayPlanDto;
import Project.AiPlanner.Plan.Dto.day.DayPlanUpdateDto;
import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.respository.day.DayPlanRepository;
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
public class DayPlanService {
    private final DayPlanRepository dayPlanRepository;

    private final ModelMapper modelMapper;


    public boolean savePlan(DayPlanDto dayPlanDto, String id) {

        try {
            DayPlanEntity dayPlanEntity = DayPlanEntity.builder()
                    .planName(dayPlanDto.getPlanName())
                    .userId(id)
                    .planType(dayPlanDto.getPlanType())
                    .start(dayPlanDto.getStart())
                    .end(dayPlanDto.getEnd())
                    .plan(dayPlanDto.getPlan())
                    .build();

            DayPlanEntity savedEntity = dayPlanRepository.save(dayPlanEntity);

            return savedEntity != null; // 저장이 성공했으면 true, 실패했으면 false 반환
        } catch (Exception e) {
            // 예외 발생 시 로깅 또는 다른 처리를 추가할 수 있습니다.
            log.error("FixPlan save failed : {}", e);
            return false; // 예외 발생 시 false 반환
        }
    }
    public List<DayPlanDto> convertToFixDtoList(List<DayPlanEntity> entities) {
        return entities.stream()
                .map(this::convertToFixDto)
                .collect(Collectors.toList());
    }

    public DayPlanDto convertToFixDto(DayPlanEntity entity) {
        return modelMapper.map(entity, DayPlanDto.class);
        // Assuming ModelMapper is used for mapping between entity and DTO
    }


    public DayPlanEntity getPlanById(int id) {
        return dayPlanRepository.findById(id).orElse(null);
    }

    @Transactional   //일반적으로 DB 데이터를 등록/수정/삭제하는 Service메서드는 @Transactional을 필수적으로 가져간다.
    public boolean deleteDayPlan(Integer planId,String userId) {
        try {
            log.info("planid={}",planId);
            dayPlanRepository.deleteByUserIdAndPlanId(userId, planId);
            return true;
        } catch (Exception e) {

            return false;
        }
    }
    @Transactional
    public boolean updateDayPlan(Integer planId,String userId, DayPlanUpdateDto dayPlanUpdateDto) {
        try {
            Optional<DayPlanEntity> optionalDayPlan = dayPlanRepository.findByPlanIdAndUserId(planId,userId);

            if (optionalDayPlan.isPresent()) {
                DayPlanEntity dayPlan = optionalDayPlan.get();
                if (!dayPlan.getUserId().equals(userId)) {
                    return false; // User does not have permission to update this plan
                }
                // Check and update fields if they are not null in the DTO
                if (dayPlanUpdateDto.getPlanName() != null) {
                    dayPlan.setPlanName(dayPlanUpdateDto.getPlanName());
                }
                if (dayPlanUpdateDto.getPlanType() != null) {
                    dayPlan.setPlanType(dayPlanUpdateDto.getPlanType());
                }
                if (dayPlanUpdateDto.getStart() != null) {
                    dayPlan.setStart(dayPlanUpdateDto.getStart());
                }
                if (dayPlanUpdateDto.getEnd() != null) {
                    dayPlan.setEnd(dayPlanUpdateDto.getEnd());
                }
                if (dayPlanUpdateDto.getPlan() != null) {
                    dayPlan.setPlan(dayPlanUpdateDto.getPlan());
                }

                // Ensure the planId matches before saving changes
                if (dayPlan.getPlanId() == dayPlanUpdateDto.getPlanId()) {
                    dayPlanRepository.save(dayPlan);
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


    // 필요한 다른 비즈니스 로직을 구현할 수 있음
