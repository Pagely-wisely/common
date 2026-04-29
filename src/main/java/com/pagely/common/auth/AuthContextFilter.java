package com.pagely.common.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Gateway가 주입한 인증 헤더를 UserContextHolder에 전파.
 *
 * <p>OncePerRequestFilter — forward/include 시 중복 실행 방지.</p>
 * <p>헤더 부재 시 미인증으로 통과. finally에서 clear()로 스레드 풀 누수 방지.</p>
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class AuthContextFilter extends OncePerRequestFilter {

    private static final String HEADER_USER_ID = "X-User-Id";
    private static final String HEADER_USER_ROLE = "X-User-Role";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        try {
            populateContext(request);
            chain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
        }
    }

    private void populateContext(HttpServletRequest request) {
        String userIdHeader = request.getHeader(HEADER_USER_ID);
        String roleHeader = request.getHeader(HEADER_USER_ROLE);

        if (userIdHeader == null || roleHeader == null) {
            log.trace("인증 헤더 부재 — 미인증 요청: path={}", request.getRequestURI());
            return;
        }
        try {
            UUID userId = UUID.fromString(userIdHeader);
            Role role = Role.valueOf(roleHeader);
            UserContextHolder.set(UserContext.of(userId, role));
            log.debug("인증 컨텍스트 설정: userId={}, role={}", userId, role);
        } catch (IllegalArgumentException e) {
            log.warn("인증 헤더 형식 오류 — 무시: userIdHeader={}, roleHeader={}",
                    userIdHeader, roleHeader);
        }
    }
}
