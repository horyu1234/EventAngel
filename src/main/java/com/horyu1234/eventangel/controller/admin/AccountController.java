package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Account;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.factory.SessionAttributeNameFactory;
import com.horyu1234.eventangel.form.LoginForm;
import com.horyu1234.eventangel.service.AccountService;
import com.horyu1234.eventangel.service.ReCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    private ReCaptchaService reCaptchaService;
    private AccountService accountService;

    @Autowired
    public AccountController(ReCaptchaService reCaptchaService, AccountService accountService) {
        this.reCaptchaService = reCaptchaService;
        this.accountService = accountService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        if (session.getAttribute(SessionAttributeNameFactory.LOGIN_USERNAME) != null) {
            return View.ADMIN_EVENT_SETTING.toRedirect();
        }

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_LOGIN.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginReceive(Model model, LoginForm loginForm, HttpSession session,
                               @RequestParam(name = "g-recaptcha-response") String reCaptchaResponse) {
        String clientIpAddress = getClientIpAddress();
        if (!reCaptchaService.verifyReCaptcha(clientIpAddress, reCaptchaResponse)) {
            LOGGER.info("[{}] ReCaptcha 인증에 실패하였습니다. {}", clientIpAddress, loginForm.getUsername());

            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.FAIL_RECAPTCHA.toView());

            return View.LAYOUT.getTemplateName();
        }

        Account account = accountService.login(loginForm.getUsername(), loginForm.getPassword());

        if (account == null) {
            model.addAttribute("loginFailed", true);
            model.addAttribute(ModelAttributeNameFactory.LOGIN_USERNAME, loginForm.getUsername());
            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_LOGIN.toView());

            return View.LAYOUT.getTemplateName();
        }

        session.setAttribute(SessionAttributeNameFactory.LOGIN_USERNAME, account.getUsername());
        session.setAttribute(SessionAttributeNameFactory.LOGIN_NICKNAME, account.getNickname());

        LOGGER.info("{} 님이 로그인하셨습니다.", loginForm.getUsername());

        return View.ADMIN_EVENT_SETTING.toRedirect();
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute(SessionAttributeNameFactory.LOGIN_USERNAME) == null) {
            return View.ADMIN_LOGIN.toRedirect();
        }

        session.removeAttribute(SessionAttributeNameFactory.LOGIN_USERNAME);
        session.removeAttribute(SessionAttributeNameFactory.LOGIN_NICKNAME);

        return View.ADMIN_LOGIN.toRedirect();
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
