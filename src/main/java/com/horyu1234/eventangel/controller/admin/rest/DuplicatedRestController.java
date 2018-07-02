package com.horyu1234.eventangel.controller.admin.rest;

import com.horyu1234.eventangel.domain.DuplicatedApplicant;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin/duplicated")
@RestController
public class DuplicatedRestController {
    private EventService eventService;
    private ApplicantService applicantService;

    @Autowired
    public DuplicatedRestController(EventService eventService, ApplicantService applicantService) {
        this.eventService = eventService;
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/getDuplicated", method = RequestMethod.POST)
    public List<DuplicatedApplicant> getDuplicated(String columnName) {
        Event currentEvent = eventService.getCurrentEvent();

        return applicantService.getDuplicatedApplicant(currentEvent.getEventId(), columnName);
    }
}
