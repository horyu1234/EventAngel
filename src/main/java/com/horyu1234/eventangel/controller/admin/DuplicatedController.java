package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/admin/duplicated")
@Controller
public class DuplicatedController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String duplicated(Model model) {
        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_DUPLICATED.toView());

        return View.LAYOUT.getTemplateName();
    }
}
