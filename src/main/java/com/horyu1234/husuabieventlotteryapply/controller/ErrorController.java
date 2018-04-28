package com.horyu1234.husuabieventlotteryapply.controller;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by horyu on 2018-04-16
 */
@RequestMapping("/error")
@Controller
public class ErrorController {

    private static final String VIEW_ERROR_404 = "view/error/404";

    @RequestMapping("/404")
    public String notFound(Model model) {
        model.addAttribute(ModelAttributeNames.VIEW_NAME, VIEW_ERROR_404);

        return ViewNames.LAYOUT;
    }
}
