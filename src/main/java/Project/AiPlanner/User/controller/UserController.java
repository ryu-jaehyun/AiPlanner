package Project.AiPlanner.User.controller;

import Project.AiPlanner.User.Dto.UserFormDto;
import Project.AiPlanner.User.Dto.UserPwRequestDto;
import Project.AiPlanner.User.entity.UserEntity;
import Project.AiPlanner.User.repository.UserRepository;
import Project.AiPlanner.User.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    //아이디 중복 검증
    @CrossOrigin(origins = "*")
    @PostMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestBody Map<String,String> request){
        String userId = request.get("userId");

        //아이디 빈값? -> 입력하라고 요청+400 error
        if (userId == null || userId.isEmpty()) {
            return new ResponseEntity<>("아이디를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        //아이디 중복 x? -> 그대로 회원가입 절차진행
        else if(userService.isUserIdUnique(userId)){
            return new ResponseEntity<>("아이디 생성 가능",HttpStatus.OK);
        }
        //아이디 중복 ? -> 중복이라고 에러 반환
        else {
            return new ResponseEntity<>("아이디 중복,다른아이디를 입력해주세요",HttpStatus.CONFLICT);
        }
    }

    //아이디찾기
    @CrossOrigin(origins = "*")
    @PostMapping("/findId")
    public ResponseEntity<String> findId(@RequestBody Map<String,String> request){
        String phoneNum = request.get("phoneNum");
        //아이디 빈값? -> 입력하라고 요청+400 error

        String userId = userRepository.findUserIdByPhoneNum(phoneNum);
        if (userId != null) {
            String message = "찾으시는 id는 " + userId + " 값 입니다";
            return ResponseEntity.ok(message);
        } else if (userId==null) {
            return new ResponseEntity<>("핸드폰번호를 입력해주세요", HttpStatus.BAD_REQUEST);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 id가 없습니다.");
        }
    }
    //비밀번호 찾기
    @CrossOrigin(origins = "*")
    @PostMapping("/findPw")
    public ResponseEntity<String> findPw( @Valid @RequestBody UserPwRequestDto userPwRequestDto) {

        String userPw = userService.getUserPassword(userPwRequestDto);
        if (userPw != null) {
            String message = "찾으시는 password는 " + userPw + " 값 입니다";
            return ResponseEntity.ok(message);
        }
        else if (userPw==null) {
            return new ResponseEntity<>("입력정보를 다시 입력해주세요", HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 password가 없습니다.");
        }






    }



    //회원가입
    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createUser( @Valid @RequestBody UserFormDto userFormDto) {

        String userId = userFormDto.getUserId();
        boolean isUnique = userService.isUserIdUnique(userId);
        if (!isUnique) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 아이디입니다. 아이디 중복확인을 눌러주세요");
        }
        userService.signup(userFormDto);

         return ResponseEntity.ok("회원가입이 완료되었습니다.");
        }

    }
