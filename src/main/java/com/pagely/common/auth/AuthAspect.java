package com.pagely.common.auth;

import com.pagely.common.auth.annotation.AuthRequired;
import com.pagely.common.exception.BusinessException;
import com.pagely.common.exception.CommonErrorCode;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AuthAspect {

    @Around("@annotation(authRequired)")
    public Object checkAuth(ProceedingJoinPoint pjp, AuthRequired authRequired) throws Throwable {
        if (!UserContextHolder.isAuthenticated()) {
            log.debug("인증 거부: 미인증 — {}", pjp.getSignature().toShortString());
            throw new BusinessException(CommonErrorCode.UNAUTHORIZED);
        }

        Role[] requiredRoles = authRequired.role();
        if (requiredRoles.length > 0) {
            Role currentRole = UserContextHolder.getCurrentRoleOrThrow();
            if (!Arrays.asList(requiredRoles).contains(currentRole)) {
                log.warn("권한 거부: role={}, required={}, method={}",
                        currentRole, Arrays.toString(requiredRoles),
                        pjp.getSignature().toShortString());
                throw new BusinessException(CommonErrorCode.FORBIDDEN);
            }
        }

        return pjp.proceed();
    }
}
