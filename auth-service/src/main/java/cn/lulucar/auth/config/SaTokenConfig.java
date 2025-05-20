package cn.lulucar.auth.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置类
 * @author wxl
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，并设置优先级
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/auth/login", "/auth/register", "/auth/change-password");
    }
}