package com.pagely.common.auth;

/**
 * 서비스 전역 사용자 권한 등급.
 *
 * <p>Gateway 가 X-User-Role 헤더로 주입하며,
 * AuthContextFilter 가 파싱하여 UserContextHolder 에 저장.</p>
 */
public enum Role {
    USER,
    CREATOR,
    MASTER
}
