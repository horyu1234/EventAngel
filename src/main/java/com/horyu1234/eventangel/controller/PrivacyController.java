package com.horyu1234.eventangel.controller;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
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
        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.PRIVACY.toView());

        return View.LAYOUT.getTemplateName();
    }
}
