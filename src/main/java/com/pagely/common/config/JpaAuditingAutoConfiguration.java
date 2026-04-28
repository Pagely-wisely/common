package com.pagely.common.config;

import com.pagely.common.auth.UserContextAuditorAware;
import java.util.UUID;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@AutoConfiguration
@ConditionalOnClass(name = "jakarta.persistence.EntityManagerFactory")
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // JPA Auditing 기능을 켜기 (auditorAware Bean 사용)
public class JpaAuditingAutoConfiguration {
    @Bean
    public AuditorAware<UUID> auditorAware() {
        return new UserContextAuditorAware();
    }
}
