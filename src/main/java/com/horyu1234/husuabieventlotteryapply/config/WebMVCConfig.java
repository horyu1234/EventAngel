package com.horyu1234.husuabieventlotteryapply.config;

import com.horyu1234.husuabieventlotteryapply.constant.View;
import com.horyu1234.husuabieventlotteryapply.interceptor.AuthenticationInterceptor;
import com.horyu1234.husuabieventlotteryapply.interceptor.RequestLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {
    private RequestLoggingInterceptor requestLoggingInterceptor;
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public void setRequestLoggingInterceptor(RequestLoggingInterceptor requestLoggingInterceptor) {
        this.requestLoggingInterceptor = requestLoggingInterceptor;
    }

    @Autowired
    public void setAuthenticationInterceptor(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(requestLoggingInterceptor)
//                .addPathPatterns("/**");

        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns(View.ADMIN_LOGIN.toPath())
                .excludePathPatterns("/admin/register/**")
                .excludePathPatterns("/admin/logout");
    }
}
