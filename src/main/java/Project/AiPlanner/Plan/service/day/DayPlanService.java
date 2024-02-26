package Project.AiPlanner.Plan.service.day;

import Project.AiPlanner.Plan.Dto.day.DayPlanDto;
import Project.AiPlanner.Plan.Dto.day.DayPlanUpdateDto;
import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import Project.AiPlanner.Plan.respository.day.DayPlanRepository;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import Project.AiPlanner.Setting.dto.DayPlanAlarmDto;
import Project.AiPlanner.Setting.dto.DaySuccessDto;
import Project.AiPlanner.Setting.dto.DayTypeColorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayPlanService {
    private final DayPlanRepository dayPlanRepository;
    private final MonthPlanRepository monthPlanRepository;
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
                    .color(dayPlanDto.getColor())
                    .dayOfWeek(dayPlanDto.getDayOfWeek())
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
    public boolean deleteDayPlan(Integer planId, String userId) {
        try {
            log.info("planid={}", planId);
            dayPlanRepository.deleteByUserIdAndPlanId(userId, planId);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    @Transactional
    public boolean updateDayPlan(Integer planId, String userId, DayPlanUpdateDto dayPlanUpdateDto) {
        try {
            Optional<DayPlanEntity> optionalDayPlan = dayPlanRepository.findByPlanIdAndUserId(planId, userId);

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

    @Transactional
    public String updateSuccessAndGetAverage(DaySuccessDto daySuccessDto) {
        String userId = daySuccessDto.getUserId();
        Integer planId = daySuccessDto.getPlanId();
        Integer success = daySuccessDto.getSuccess();

        // 해당 userId와 planId에 해당하는 일정을 찾아 success 값을 업데이트
        Optional<DayPlanEntity> dayPlanOptional = dayPlanRepository.findByPlanIdAndUserId(planId, userId);
        dayPlanOptional.ifPresent(dayPlan -> {
            dayPlan.setSuccess(success);
            dayPlanRepository.save(dayPlan);
        });
        Optional<MonthPlanEntity> monthPlanOptional = monthPlanRepository.findByPlanIdAndUserId(planId, userId);
        monthPlanOptional.ifPresent(monthPlan -> {
            monthPlan.setSuccess(success);
            monthPlanRepository.save(monthPlan);
        });
        // 전체 일정에 대한 success 평균 계산
        double totalDayPlans = dayPlanRepository.countByUserId(userId);
        double totalMonthPlans = monthPlanRepository.countByUserId(userId);
        Optional<Integer> dayPlanSuccessSumOptional = Optional.ofNullable(dayPlanRepository.sumSuccessByUserId(userId));
        Optional<Integer> monthPlanSuccessSumOptional = Optional.ofNullable(monthPlanRepository.sumSuccessByUserId(userId));

        int dayPlanSuccessSum = dayPlanSuccessSumOptional.orElse(0);
        int monthPlanSuccessSum = monthPlanSuccessSumOptional.orElse(0);

        double averageSuccess = ((double) dayPlanSuccessSum + monthPlanSuccessSum) / (totalDayPlans + totalMonthPlans);
        int roundedAverage = (int) Math.round(averageSuccess * 100); // 반올림 및 정수로 변환
        String result = String.format("%d%%", roundedAverage); // 문자열로 변환하여 출력

        return result;
    }

    public List<DayTypeColorDto> getUniquePlanTypesAndColors(String userId) {
        List<DayPlanEntity> userPlans = dayPlanRepository.findByUserId(userId);
        Map<String, String> typeColorMap = new HashMap<>();

        for (DayPlanEntity plan : userPlans) {
            String planType = plan.getPlanType();
            String color = plan.getColor();

            // 중복되지 않는 planType과 color를 Map에 추가
            if (!typeColorMap.containsKey(planType)) {
                typeColorMap.put(planType, color);
            }
        }

        // DayTypeColorDto 리스트 생성
        List<DayTypeColorDto> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : typeColorMap.entrySet()) {
            String planType = entry.getKey();
            String color = entry.getValue();

            // DayTypeColorDto에 planType과 color를 설정하여 리스트에 추가
            DayTypeColorDto dto = new DayTypeColorDto();
            dto.setPlanType(planType);
            dto.setColor(color);

            result.add(dto);
        }

        return result;
    }

    public List<DayPlanAlarmDto> getUpcomingDayPlans(LocalDateTime startDate) {
        // 이전 날짜 이후에 시작하는 모든 일정을 조회
        return dayPlanRepository.findByStartAfter(startDate);
    }
}


// 필요한 다른 비즈니스 로직을 구현할 수 있음
