package Project.AiPlanner.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException{
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401  --> 원래 401인데 임시 테스트를 위해서 200으로 함 나중에 다 코딩하면 401로 바꿔서 해보기
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}