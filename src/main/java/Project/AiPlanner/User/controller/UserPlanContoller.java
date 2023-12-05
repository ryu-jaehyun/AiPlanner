package Project.AiPlanner.User.controller;


import Project.AiPlanner.User.Dto.UserPlanTypeColorDto;
import Project.AiPlanner.User.service.UserPlanTypeService;
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
@RequestMapping("/user-plan")
public class UserPlanContoller {
    private final UserPlanTypeService userPlanTypeService;



    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public ResponseEntity<String> addUserPlanType(@Valid @RequestBody UserPlanTypeColorDto userPlanTypeColorDto) {
        String userId = SecurityUtil.getCurrentUserId();
        userPlanTypeService.saveUserPlanType(userId, userPlanTypeColorDto);

        return ResponseEntity.ok("일정 타입 등록완료!");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public ResponseEntity<List<UserPlanTypeColorDto>> getUserPlanTypes() {
        String userId = SecurityUtil.getCurrentUserId();
        userPlanTypeService.addDefaultPlanTypeAndColor(userId);
        List<UserPlanTypeColorDto> userPlanTypes = userPlanTypeService.getUserPlanTypesByUserId(userId);
        return ResponseEntity.ok(userPlanTypes);
    }
}



