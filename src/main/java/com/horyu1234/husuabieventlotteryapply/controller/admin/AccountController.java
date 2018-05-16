package com.horyu1234.husuabieventlotteryapply.controller.admin;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.SessionAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.View;
import com.horyu1234.husuabieventlotteryapply.domain.Account;
import com.horyu1234.husuabieventlotteryapply.form.LoginForm;
import com.horyu1234.husuabieventlotteryapply.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by horyu on 2018-04-03
 */
@RequestMapping("/admin")
@Controller
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        if (session.getAttribute(SessionAttributeNames.LOGIN_USERNAME) != null) {
            return View.ADMIN_EVENT_SETTING.toRedirect();
        }

        model.addAttribute(ModelAttributeNames.VIEW_NAME, View.ADMIN_LOGIN.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginReceive(Model model, LoginForm loginForm, HttpSession session) {
        Account account = accountService.login(loginForm.getUsername(), loginForm.getPassword());

        if (account == null) {
            model.addAttribute("loginFailed", true);
            model.addAttribute(ModelAttributeNames.LOGIN_USERNAME, loginForm.getUsername());
            model.addAttribute(ModelAttributeNames.VIEW_NAME, View.ADMIN_LOGIN.toView());

            return View.LAYOUT.getTemplateName();
        }

        session.setAttribute(SessionAttributeNames.LOGIN_USERNAME, account.getUsername());
        session.setAttribute(SessionAttributeNames.LOGIN_NICKNAME, account.getNickname());

        LOGGER.info(loginForm.getUsername() + " 님이 로그인하셨습니다.");

        return View.ADMIN_EVENT_SETTING.toRedirect();
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute(SessionAttributeNames.LOGIN_USERNAME) == null) {
            return View.ADMIN_LOGIN.toRedirect();
        }

        session.removeAttribute(SessionAttributeNames.LOGIN_USERNAME);
        session.removeAttribute(SessionAttributeNames.LOGIN_NICKNAME);

        return View.ADMIN_LOGIN.toRedirect();
    }
}
