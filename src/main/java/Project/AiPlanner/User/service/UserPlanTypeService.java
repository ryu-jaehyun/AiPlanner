package Project.AiPlanner.User.service;


import Project.AiPlanner.User.Dto.UserPlanTypeColorDto;
import Project.AiPlanner.User.entity.UserPlanTypeEntity;
import Project.AiPlanner.User.repository.UserPlanTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPlanTypeService {
    private final UserPlanTypeRepository userPlanTypeRepository;
    // 새로운 user plan type 등록


    @Transactional
    public void saveUserPlanType(String userId, UserPlanTypeColorDto userPlanTypeColorDto) {
        UserPlanTypeEntity userPlanType = new UserPlanTypeEntity();
        userPlanType.setUserId(userId);
        userPlanType.setColor(userPlanTypeColorDto.getColor());
        userPlanType.setPlanType(userPlanTypeColorDto.getPlanType());

        userPlanTypeRepository.save(userPlanType);
    }

    @Transactional
    public List<UserPlanTypeColorDto> getUserPlanTypesByUserId(String userId) {
        List<UserPlanTypeEntity> userPlanTypeEntities = userPlanTypeRepository.findByUserId(userId);
        return userPlanTypeEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserPlanTypeColorDto convertToDTO(UserPlanTypeEntity entity) {
        UserPlanTypeColorDto dto = new UserPlanTypeColorDto();
        dto.setColor(entity.getColor());
        dto.setPlanType(entity.getPlanType());
        return dto;
    }

    @Transactional
    public void addDefaultPlanTypeAndColor(String userId) {
        List<UserPlanTypeEntity> existingDefaults = userPlanTypeRepository.findByUserId(userId);
        if (existingDefaults.isEmpty()) {
            List<UserPlanTypeEntity> defaultPlanTypeColors = new ArrayList<>();
            defaultPlanTypeColors.add(new UserPlanTypeEntity(userId, "#DC8686", "식사"));
            defaultPlanTypeColors.add(new UserPlanTypeEntity(userId, "#3081D0", "공부"));
            defaultPlanTypeColors.add(new UserPlanTypeEntity(userId, "#6DB9EF", "휴식"));
            defaultPlanTypeColors.add(new UserPlanTypeEntity(userId, "#FF6C22", "여가"));
            defaultPlanTypeColors.add(new UserPlanTypeEntity(userId, "#9ADE7B", "운동"));

            userPlanTypeRepository.saveAll(defaultPlanTypeColors);
        }
    }
}
