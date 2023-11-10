package Project.AiPlanner.User.controller;

import Project.AiPlanner.User.Dto.UserFormDto;
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




    //회원가입
    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createUser( @Valid @RequestBody UserFormDto userFormDto) {

         userService.signup(userFormDto);

         return ResponseEntity.ok("회원가입이 완료되었습니다.");
        }

    }
