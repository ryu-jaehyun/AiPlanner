package Project.AiPlanner.Plan.service.month;


import Project.AiPlanner.Plan.Dto.month.MonthPlanDto;
import Project.AiPlanner.Plan.Dto.month.MonthPlanUpdateDto;
import Project.AiPlanner.Plan.Dto.month.MonthSuccessDto;
import Project.AiPlanner.Plan.Dto.month.MonthTypeColorDto;
import Project.AiPlanner.Plan.entity.day.DayPlanEntity;
import Project.AiPlanner.Plan.entity.month.MonthPlanEntity;
import Project.AiPlanner.Plan.respository.day.DayPlanRepository;
import Project.AiPlanner.Plan.respository.month.MonthPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MonthPlanService {


    private final MonthPlanRepository monthPlanRepository;
    private final DayPlanRepository dayPlanRepository;
    private final ModelMapper modelMapper;

    public boolean saveMonthPlan(MonthPlanDto monthPlanDto, String id) {

        try {
            MonthPlanEntity monthPlanEntity = MonthPlanEntity.builder()
                    .planName(monthPlanDto.getPlanName())
                    .userId(id)
                    .planType(monthPlanDto.getPlanType())
                    .start(monthPlanDto.getStart())
                    .end(monthPlanDto.getEnd())
                    .color(monthPlanDto.getColor())
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

    @Transactional
    public String updateSuccessAndGetAverage(MonthSuccessDto monthSuccessDto) {
        String userId = monthSuccessDto.getUserId();
        Integer planId = monthSuccessDto.getPlanId();
        Integer success = monthSuccessDto.getSuccess();

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
    public List<MonthTypeColorDto> getUniquePlanTypesAndColors(String userId) {
        List<MonthPlanEntity> userPlans = monthPlanRepository.findByUserId(userId);
        Map<String, String> typeColorMap = new HashMap<>();

        for (MonthPlanEntity plan : userPlans) {
            String planType = plan.getPlanType();
            String color = plan.getColor();

            // 중복되지 않는 planType과 color를 Map에 추가
            if (!typeColorMap.containsKey(planType)) {
                typeColorMap.put(planType, color);
            }
        }

        // MonthTypeColorDto 리스트 생성
        List<MonthTypeColorDto> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : typeColorMap.entrySet()) {
            String planType = entry.getKey();
            String color = entry.getValue();

            // MonthTypeColorDto planType과 color를 설정하여 리스트에 추가
            MonthTypeColorDto dto = new MonthTypeColorDto();
            dto.setPlanType(planType);
            dto.setColor(color);

            result.add(dto);
        }

        return result;
    }
}
