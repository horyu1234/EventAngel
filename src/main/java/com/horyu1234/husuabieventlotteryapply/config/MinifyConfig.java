package com.horyu1234.husuabieventlotteryapply.config;

import com.horyu1234.husuabieventlotteryapply.filter.MinifyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by horyu on 2018-01-15
 */
@Configuration
public class MinifyConfig {
    @Bean
    public FilterRegistrationBean<MinifyFilter> getFilterRegistrationBean() {
        return new FilterRegistrationBean<>(new MinifyFilter());
    }
}
