package Project.AiPlanner.Setting.controller;


import Project.AiPlanner.User.Dto.UserPlanTypeColorDto;
import Project.AiPlanner.Setting.service.UserPlanTypeService;
import Project.AiPlanner.Util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/setting")
public class UserPlanContoller {
    private final UserPlanTypeService userPlanTypeService;



    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    @PostMapping("/type/add")
    public ResponseEntity<String> addUserPlanType(@Valid @RequestBody UserPlanTypeColorDto userPlanTypeColorDto) {
        String userId = SecurityUtil.getCurrentUserId();
        userPlanTypeService.saveUserPlanType(userId, userPlanTypeColorDto);

        return ResponseEntity.ok("일정 타입 등록완료!");
    }

    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    @GetMapping("/type/get")
    public ResponseEntity<List<UserPlanTypeColorDto>> getUserPlanTypes() {
        String userId = SecurityUtil.getCurrentUserId();
        userPlanTypeService.addDefaultPlanTypeAndColor(userId);
        List<UserPlanTypeColorDto> userPlanTypes = userPlanTypeService.getUserPlanTypesByUserId(userId);
        return ResponseEntity.ok(userPlanTypes);
    }
}



