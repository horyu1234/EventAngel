package com.horyu1234.husuabieventlotteryapply.controller;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.ViewNames;
import com.horyu1234.husuabieventlotteryapply.domain.Applicant;
import com.horyu1234.husuabieventlotteryapply.form.CheckForm;
import com.horyu1234.husuabieventlotteryapply.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by horyu on 2018-04-02
 */
@Controller
public class CheckController {
    private static final String VIEW_CHECK_CHECK = "view/check/check";
    private static final String VIEW_CHECK_EXIST_APPLY = "view/check/existApply";
    private static final String VIEW_CHECK_NOT_EXIST_APPLY = "view/check/notExistApply";
    private ApplicantService applicantService;

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(Model model) {
        model.addAttribute(ModelAttributeNames.VIEW_NAME, VIEW_CHECK_CHECK);

        return ViewNames.LAYOUT;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkReceive(Model model, CheckForm checkForm) {
        Applicant storedApplicant = applicantService.getApply(checkForm.getEmail());
        if (storedApplicant != null) {
            model.addAttribute("applicant", storedApplicant);
            model.addAttribute(ModelAttributeNames.VIEW_NAME, VIEW_CHECK_EXIST_APPLY);

            return ViewNames.LAYOUT;
        }

        model.addAttribute(ModelAttributeNames.VIEW_NAME, VIEW_CHECK_NOT_EXIST_APPLY);

        return ViewNames.LAYOUT;
    }
}
