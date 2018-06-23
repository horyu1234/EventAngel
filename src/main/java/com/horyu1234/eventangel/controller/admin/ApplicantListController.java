package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
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
    private EventService eventService;
    private ApplicantService applicantService;

    @Autowired
    public ApplicantListController(EventService eventService, ApplicantService applicantService) {
        this.eventService = eventService;
        this.applicantService = applicantService;
    }

    @RequestMapping("/")
    public String applicantList(Model model) {
        Event currentEvent = eventService.getCurrentEvent();

        model.addAttribute("applyCount", applicantService.getApplyCount(currentEvent.getEventId(), true));
        model.addAttribute("applicantList", applicantService.getRecent50ApplicantList(currentEvent.getEventId()));

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_APPLICANT_LIST.toView());

        return View.LAYOUT.getTemplateName();
    }
}
