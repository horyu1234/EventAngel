package com.horyu1234.husuabieventlotteryapply.config;

import com.horyu1234.husuabieventlotteryapply.constant.View;
import com.horyu1234.husuabieventlotteryapply.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public void setAuthenticationInterceptor(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns(View.ADMIN_LOGIN.toPath())
                .excludePathPatterns("/admin/register/**")
                .excludePathPatterns("/admin/logout");
    }
}
