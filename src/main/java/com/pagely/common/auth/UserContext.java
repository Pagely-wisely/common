package com.pagely.common.auth;

import java.util.UUID;

/**
 * 현재 요청을 처리 중인 인증된 유저의 컨텍스트 정보.
 *
 * <p>Gateway가 주입한 헤더(X-User-Id, X-User-Role)에서 추출.
 * ThreadLocal에 담겨 요청 처리 중 어디서든 접근 가능.</p>
 */
public record UserContext(
        UUID userId,
        Role role
) {
    /**
     * userId, role로 UserContext 생성. null 불가.
     */
    public static UserContext of(UUID userId, Role role) {
        if (userId == null) {
            throw new IllegalArgumentException("userId는 null일 수 없습니다.");
        }
        if (role == null) {
            throw new IllegalArgumentException("role은 null일 수 없습니다.");
        }
        return new UserContext(userId, role);
    }
}
