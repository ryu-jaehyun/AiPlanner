package Project.AiPlanner.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {}

    //현재 인증된 사용자의 이름(username)을 얻는 메서드
    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //authentication이 null이면, 즉, 사용자가 인증되지 않은 경우, 로그에 "Security Context에 인증 정보가 없습니다."라는 디버그 메시지를 출력하고 빈 Optional을 반환합니다.
        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }
    // 사용자 이름을 감싸고 있는 Optional 객체를 반환합니다. Optional을 사용하여 사용자 이름이 null인 경우에 대비할 수 있습니다.
        return Optional.ofNullable(username);
    }
    public static String getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        return authentication.getName();
    }
}