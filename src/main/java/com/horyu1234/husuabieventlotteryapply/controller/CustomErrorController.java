package com.horyu1234.husuabieventlotteryapply.controller;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.View;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by horyu on 2018-04-16
 */
@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        int statusCode = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute(ModelAttributeNames.VIEW_NAME, View.ERROR_404.toView());
        } else {
            model.addAttribute(ModelAttributeNames.VIEW_NAME, View.ERROR_500.toView());
        }

        return View.LAYOUT.getTemplateName();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
