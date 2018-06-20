package com.horyu1234.eventangel.interceptor;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.factory.SessionAttributeNameFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginUsername = (String) request.getSession().getAttribute(SessionAttributeNameFactory.LOGIN_USERNAME);
        if (loginUsername == null) {
            response.sendRedirect(View.ADMIN_LOGIN.toPath());
            return false;
        }

        return true;
    }
}
