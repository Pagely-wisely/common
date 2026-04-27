package com.pagely.common.config;

import com.pagely.common.pagination.PageRequestArgumentResolver;
import com.pagely.common.web.GlobalExceptionHandler;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 환경에서 공통 빈 자동 등록.
 *
 * <p>{@link GlobalExceptionHandler} 를 직접 정의한 서비스에서는 이 설정이 적용되지 않습니다.
 * <p>{@link PageRequestArgumentResolver} 는 항상 등록됩니다.
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CommonWebAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageRequestArgumentResolver());
    }
}
