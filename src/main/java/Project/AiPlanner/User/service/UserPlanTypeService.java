package Project.AiPlanner.User.service;


import Project.AiPlanner.User.Dto.UserPlanTypeColorDto;
import Project.AiPlanner.User.entity.UserPlanTypeEntity;
import Project.AiPlanner.User.repository.UserPlanTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<UserPlanTypeColorDto> getUserPlanTypesByUserId(String userId) {
        List<UserPlanTypeEntity> userPlanTypeEntities = userPlanTypeRepository.findByUserId(userId);
        return userPlanTypeEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserPlanTypeColorDto convertToDTO(UserPlanTypeEntity entity) {
        UserPlanTypeColorDto dto = new UserPlanTypeColorDto();
        dto.setColor(entity.getColor());
        dto.setPlanType(entity.getPlanType());
        return dto;
    }
}
