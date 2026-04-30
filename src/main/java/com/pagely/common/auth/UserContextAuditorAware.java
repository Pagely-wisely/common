package com.pagely.common.auth;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;

/**
 * JPA Auditing의 createdBy / updatedBy 자동 채움.
 *
 * <p>미인증 요청(스케줄러, 이벤트 기반 자동 처리 등)은 SystemUUID 반환.
 * 각 서비스의 @EnableJpaAuditing과 함께 동작.</p>
 */
public class UserContextAuditorAware implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        return Optional.of(UserContextHolder.getUserIdOrSystem());
    }
}
