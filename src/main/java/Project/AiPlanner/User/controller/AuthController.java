package Project.AiPlanner.User.controller;

import Project.AiPlanner.User.Dto.LoginDto;
import Project.AiPlanner.jwt.JwtFilter;
import Project.AiPlanner.jwt.TokenDto;
import Project.AiPlanner.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://ec2-13-125-51-122.ap-northeast-2.compute.amazonaws.com:3000/")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {


        log.info("loginDto= {},{}", loginDto.getUserId(), loginDto.getUserPw());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getUserPw());

        log.info("authenticatontoken={}", authenticationToken);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("authentication = " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 액세스 토큰 생성
        String accessToken = tokenProvider.createToken(authentication);

        // 리프레시 토큰 생성
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        // 클라이언트에게 액세스 토큰과 리프레시 토큰 반환
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new TokenDto(accessToken, refreshToken));
    }
}