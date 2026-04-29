package com.pagely.common.config;

import com.pagely.common.auth.AuthAspect;
import com.pagely.common.auth.AuthContextFilter;
import com.pagely.common.auth.UserContextAuditorAware;
import com.pagely.common.auth.resolver.CurrentUserIdResolver;
import com.pagely.common.auth.resolver.CurrentUserRoleResolver;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인증 인프라 자동 설정.
 *
 * <p>등록 대상:</p>
 * <ul>
 *   <li>AuthContextFilter — X-User-Id/Role 헤더 → ThreadLocal</li>
 *   <li>AuthAspect — @AuthRequired AOP 권한 체크</li>
 *   <li>CurrentUserIdResolver / CurrentUserRoleResolver — 파라미터 자동 주입</li>
 *   <li>UserContextAuditorAware — JPA Auditing (spring-data 존재 시만)</li>
 * </ul>
 */
@AutoConfiguration
@EnableAspectJAutoProxy
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AuthAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public AuthContextFilter authContextFilter() {
        return new AuthContextFilter();
    }

    @Bean
    public AuthAspect authAspect() {
        return new AuthAspect();
    }

    @Bean
    @ConditionalOnClass(AuditorAware.class)
    public UserContextAuditorAware userContextAuditorAware() {
        return new UserContextAuditorAware();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserIdResolver());
        resolvers.add(new CurrentUserRoleResolver());
    }
}
