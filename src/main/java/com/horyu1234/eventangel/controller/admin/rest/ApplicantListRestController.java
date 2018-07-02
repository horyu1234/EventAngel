package com.horyu1234.eventangel.controller.admin.rest;

import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Applicant> getApplicant() {
        Event currentEvent = eventService.getCurrentEvent();

        return applicantService.getApplicantList(currentEvent.getEventId());
    }
}
