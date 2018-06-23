package com.horyu1234.eventangel.controller.admin.rest;

import com.horyu1234.eventangel.constant.EventStatus;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
import com.horyu1234.eventangel.service.EventWinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by horyu on 2018-04-16
 */
@RequestMapping("/admin/eventSetting")
@RestController
public class EventSettingRestController {
    private EventService eventService;
    private ApplicantService applicantService;
    private EventWinnerService eventWinnerService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Autowired
    public void setEventWinnerService(EventWinnerService eventWinnerService) {
        this.eventWinnerService = eventWinnerService;
    }

    @RequestMapping(value = "/endEvent", method = RequestMethod.GET)
    public String endEvent() {
        eventService.newEvent();

        return "success";
    }

    @RequestMapping(value = "/resetEventWinner", method = RequestMethod.GET)
    public String resetEventWinner() {
        Event currentEvent = eventService.getCurrentEvent();
        currentEvent.setEventStatus(EventStatus.CLOSE);

        eventWinnerService.resetEventWinner(currentEvent.getEventId());
        eventService.updateEvent(currentEvent);

        return "success";
    }
}
