package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/admin/applicantList")
@Controller
public class ApplicantListController {
    @RequestMapping("/")
    public String applicantList(Model model,
                                @RequestParam(required = false) String columnId,
                                @RequestParam(required = false) String columnName) {
        if (columnId != null && columnName != null) {
            model.addAttribute("columnId", columnId);
            model.addAttribute("columnName", columnName);
        }

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_APPLICANT_LIST.toView());

        return View.LAYOUT.getTemplateName();
    }
}
