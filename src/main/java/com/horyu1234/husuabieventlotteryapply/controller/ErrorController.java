package com.horyu1234.husuabieventlotteryapply.controller;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.View;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by horyu on 2018-04-16
 */
@RequestMapping("/error")
@Controller
public class ErrorController {
    @RequestMapping("/404")
    public String notFound(Model model) {
        model.addAttribute(ModelAttributeNames.VIEW_NAME, View.ERROR_404.toView());

        return View.LAYOUT.getTemplateName();
    }
}
