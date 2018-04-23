package com.horyu1234.husuabieventlotteryapply.controller.admin.rest;

import com.horyu1234.husuabieventlotteryapply.constant.EventStatus;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.service.ApplicantService;
import com.horyu1234.husuabieventlotteryapply.service.EventService;
import com.horyu1234.husuabieventlotteryapply.service.EventWinnerService;
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
        applicantService.truncate();

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
