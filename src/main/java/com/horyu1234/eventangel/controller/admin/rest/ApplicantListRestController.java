package com.horyu1234.eventangel.controller.admin.rest;

import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.factory.SessionAttributeNameFactory;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/admin/applicantList")
@RestController
public class ApplicantListRestController {
    private EventService eventService;
    private ApplicantService applicantService;

    @Autowired
    public ApplicantListRestController(EventService eventService, ApplicantService applicantService) {
        this.eventService = eventService;
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/getApplicant", method = RequestMethod.POST)
    public List<Applicant> getApplicant(String columnId, String columnName) {
        Event currentEvent = eventService.getCurrentEvent();

        if (columnId == null || columnName == null) {
            return applicantService.getApplicantList(currentEvent.getEventId());
        } else {
            return applicantService.getApplicantList(currentEvent.getEventId(), columnId, columnName);
        }
    }

    @RequestMapping(value = "/changeDuplicated", method = RequestMethod.POST)
    public String changeDuplicated(HttpSession session,
                                   String applyEmail, boolean isDuplicated) {
        Event currentEvent = eventService.getCurrentEvent();

        if (isDuplicated) {
            String loginUsername = (String) session.getAttribute(SessionAttributeNameFactory.LOGIN_USERNAME);

            applicantService.updateApplicantDuplicated(currentEvent.getEventId(), applyEmail, loginUsername);
        } else {
            applicantService.updateApplicantNotDuplicated(currentEvent.getEventId(), applyEmail);
        }

        return "success";
    }
}
