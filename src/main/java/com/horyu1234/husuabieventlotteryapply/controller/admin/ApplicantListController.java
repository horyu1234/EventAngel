package com.horyu1234.husuabieventlotteryapply.controller.admin;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.ViewNames;
import com.horyu1234.husuabieventlotteryapply.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by horyu on 2018-04-14
 */
@RequestMapping("/admin/applicantList")
@Controller
public class ApplicantListController {
    private ApplicantService applicantService;

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @RequestMapping("/")
    public String applicantList(Model model) {
        model.addAttribute("applyCount", applicantService.getApplyCount());
        model.addAttribute("applicantList", applicantService.getRecent50ApplicantList());

        model.addAttribute(ModelAttributeNames.VIEW_NAME, "view/admin/applicantList");

        return ViewNames.LAYOUT;
    }
}
