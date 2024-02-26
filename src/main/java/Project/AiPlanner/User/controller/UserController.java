package Project.AiPlanner.User.controller;

import Project.AiPlanner.User.Dto.AdminRegistrationDto;
import Project.AiPlanner.User.Dto.UserFormDto;
import Project.AiPlanner.User.Dto.UserPwRequestDto;
import Project.AiPlanner.User.entity.UserEntity;
import Project.AiPlanner.User.repository.UserRepository;
import Project.AiPlanner.User.service.AdminService;
import Project.AiPlanner.User.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserRepository userRepository;

    private final UserService userService;

    private final AdminService adminService;


    //아이디 중복 검증
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    @PostMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");

        //아이디 빈값? -> 입력하라고 요청+400 error
        if (userId == null || userId.isEmpty()) {
            return new ResponseEntity<>("아이디를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        //아이디 중복 x? -> 그대로 회원가입 절차진행
        else if (userService.isUserIdUnique(userId)) {
            return new ResponseEntity<>("아이디 생성 가능", HttpStatus.OK);
        }
        //아이디 중복 ? -> 중복이라고 에러 반환
        else {
            return new ResponseEntity<>("아이디 중복,다른아이디를 입력해주세요", HttpStatus.CONFLICT);
        }
    }

    //아이디찾기
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    @PostMapping("/findId")
    public ResponseEntity<String> findId(@RequestBody Map<String, String> request) {
        String phoneNum = request.get("phoneNum");
        //아이디 빈값? -> 입력하라고 요청+400 error
        log.info("phoneNum={}", phoneNum);
        Optional<UserEntity> userOptional = userRepository.findUserIdByPhoneNum(phoneNum);
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            String userId = userEntity.getUserId();
            // 여기서 userId 사용
            String message = "찾으시는 id는 " + userId + "입니다";
            return ResponseEntity.ok(message);
        } else if (phoneNum == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("전화번호를 다시입력해주세요");

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 id가 없습니다.");
        }
    }

    //비밀번호 찾기
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    @PostMapping("/findPw")
    public ResponseEntity<String> findPw(@Valid @RequestBody UserPwRequestDto userPwRequestDto) {

        String newPw = userService.updateAndReturnTempPassword(userPwRequestDto.getUserId(), userPwRequestDto.getPhoneNum());
        if (newPw != null) {


            String message = "임시비밀번호는 " + newPw + "입니다";
            return ResponseEntity.ok(message);
        } else {
            return new ResponseEntity<>("입력정보를 다시 입력해주세요", HttpStatus.BAD_REQUEST);
        }


    }


    //일반 사용자 회원가입
    @PostMapping("/signup")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserFormDto userFormDto) {

        String userId = userFormDto.getUserId();
        boolean isUnique = userService.isUserIdUnique(userId);
        if (!isUnique) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 아이디입니다. 아이디 중복확인을 눌러주세요");
        }
        userService.signup(userFormDto);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
    @PostMapping("/signup/admin")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<String> createAdmin(@Valid @RequestBody AdminRegistrationDto adminDto) {

        if (adminDto.getAdminCode()=="vo6182jsmruby1004") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 관리자 인증 코드입니다.");
        }
        adminService.registerAdmin(adminDto);
        return ResponseEntity.ok("관리자 회원가입이 완료되었습니다.");
    }


}