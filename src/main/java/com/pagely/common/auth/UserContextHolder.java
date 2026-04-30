package com.pagely.common.auth;

import com.pagely.common.exception.BusinessException;
import com.pagely.common.exception.CommonErrorCode;
import java.util.Optional;
import java.util.UUID;

/**
 * ThreadLocal 기반 인증 컨텍스트 홀더.
 *
 * <p>API 분류</p>
 * <ul>
 *   <li>{@code getXxx()} : null 가능</li>
 *   <li>{@code getXxxOrThrow()} : 미인증 시 BusinessException(UNAUTHORIZED)</li>
 *   <li>{@code findXxx()} : Optional 반환</li>
 * </ul>
 */
public final class UserContextHolder {
    // 시스템 전용 고정 UUID 선언 (DB에 존재하거나 논리적으로 약속된 값)
    public static final UUID SYSTEM_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

    private UserContextHolder() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void set(UserContext context) {
        CONTEXT.set(context);
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static UserContext get() {
        return CONTEXT.get();
    }

    public static Optional<UserContext> find() {
        return Optional.ofNullable(CONTEXT.get());
    }

    public static UserContext getOrThrow() {
        UserContext ctx = CONTEXT.get();
        if (ctx == null) {
            throw new BusinessException(CommonErrorCode.UNAUTHORIZED);
        }
        return ctx;
    }

    public static UUID getCurrentUserId() {
        UserContext ctx = CONTEXT.get();
        return ctx == null ? null : ctx.userId();
    }

    public static Optional<UUID> findCurrentUserId() {
        return find().map(UserContext::userId);
    }

    public static UUID getCurrentUserIdOrThrow() {
        return getOrThrow().userId();
    }

    public static Role getCurrentRole() {
        UserContext ctx = CONTEXT.get();
        return ctx == null ? null : ctx.role();
    }

    public static Optional<Role> findCurrentRole() {
        return find().map(UserContext::role);
    }

    public static Role getCurrentRoleOrThrow() {
        return getOrThrow().role();
    }

    public static boolean isAuthenticated() {
        return CONTEXT.get() != null;
    }

   /* 현재 사용자 ID가 없으면 SYSTEM_ID를 반환하는 메서드*/
    public static UUID getUserIdOrSystem() {
        UUID userId = getCurrentUserId();
        return (userId != null) ? userId : SYSTEM_ID;
    }
}
