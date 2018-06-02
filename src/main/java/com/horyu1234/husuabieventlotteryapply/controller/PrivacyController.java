package com.horyu1234.husuabieventlotteryapply.controller;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.View;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by horyu on 2018-05-30.
 */
@Controller
public class PrivacyController {
    @RequestMapping("/privacy")
    public String home(Model model) {
        model.addAttribute(ModelAttributeNames.VIEW_NAME, View.PRIVACY.toView());

        return View.LAYOUT.getTemplateName();
    }
}
