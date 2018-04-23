package com.horyu1234.husuabieventlotteryapply.interceptor;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestLoggingInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String url = request.getRequestURL().toString();
        if (request.getQueryString() != null) {
            url = url + "?" + request.getQueryString();
        }

        String ipAddress = getClientIpAddress();
        String requestMethod = request.getMethod();
        String viewName = null;
        if (modelAndView != null && !modelAndView.getModel().isEmpty()) {
            viewName = (String) modelAndView.getModel().get(ModelAttributeNames.VIEW_NAME);
        }
        String referer = request.getHeader("Referer");

        List<Object> objects = new ArrayList<>();
        objects.add(ipAddress);
        objects.add(requestMethod);
        objects.add(url);
        if (viewName != null) objects.add(viewName);
        if (referer != null) objects.add(referer);

        String logFormat = "[%s] \"%s %s\"" + (viewName == null ? "" : " -> \"%s\"") + (referer == null ? "" : " (%s)");
        String logMsg = String.format(logFormat, objects.toArray());

        LOGGER.info(logMsg);
    }

    private String getClientIpAddress() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip;
        if (req.getHeader("HTTP_CF_CONNECTING_IP") != null) {
            ip = req.getHeader("HTTP_CF_CONNECTING_IP");
        } else if (req.getHeader("X-FORWARDED-FOR") != null) {
            ip = req.getHeader("X-FORWARDED-FOR");
        } else {
            ip = req.getRemoteAddr();
        }

        return ip;
    }
}
