package Project.AiPlanner.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveAccessToken(httpServletRequest);
        String jwt_r = resolveRefreshToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            // 리프레시 토큰이 만료되었고, 액세스 토큰도 만료된 경우
            if (StringUtils.hasText(jwt_r) && !tokenProvider.validateRefreshToken(jwt_r)) {
                logger.debug("만료된 리프레시 토큰입니다. 재로그인이 필요합니다.");
                // 클라이언트에게 재로그인하도록 유도하는 처리
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "재로그인이 필요합니다.");
                return;
            }
            // 액세스 토큰이 만료되었고, 리프레시 토큰이 있다면 새로운 액세스 토큰 발급
            if (StringUtils.hasText(jwt_r) && tokenProvider.validateRefreshToken(jwt_r)) {
                // 새로운 액세스 토큰 생성
                String newJwt = tokenProvider.createTokenFromRefreshToken(jwt_r);

                // 생성된 새로운 액세스 토큰을 HTTP 응답 헤더에 포함시켜 클라이언트에 반환
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.setHeader(AUTHORIZATION_HEADER, "Bearer " + newJwt);
                logger.debug("새로운 액세스 토큰이 발급되었습니다, uri: {}", requestURI);
            }
        }
            filterChain.doFilter(servletRequest, servletResponse);

    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 반환
        }

        return null; // 토큰이 없는 경우
    }

    // 리프레시 토큰을 추출하는 메소드
    private String resolveRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");

        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer ")) {
            return refreshToken.substring(7); // "Bearer " 이후의 토큰 반환
        }

        return null; // 토큰이 없는 경우
    }
}