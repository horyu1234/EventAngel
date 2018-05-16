package com.horyu1234.husuabieventlotteryapply.config;

import com.horyu1234.husuabieventlotteryapply.constant.View;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by horyu on 2018-04-16
 */
@Configuration
public class ErrorPageConfig extends ServerProperties {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, View.ERROR_404.toPath()));
    }
}
