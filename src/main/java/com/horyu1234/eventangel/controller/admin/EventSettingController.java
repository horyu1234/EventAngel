package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.form.EventSettingForm;
import com.horyu1234.eventangel.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by horyu on 2018-04-04
 */
@RequestMapping("/admin/eventSetting")
@Controller
public class EventSettingController {
    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String eventSetting(Model model) {
        Event currentEvent = eventService.getCurrentEvent();
        model.addAttribute(ModelAttributeNameFactory.EVENT_ID, currentEvent.getEventId());
        model.addAttribute(ModelAttributeNameFactory.EVENT_STATUS, currentEvent.getEventStatus());
        model.addAttribute(ModelAttributeNameFactory.EVENT_TITLE, currentEvent.getEventTitle());
        model.addAttribute(ModelAttributeNameFactory.EVENT_DETAIL, currentEvent.getEventDetail());
        model.addAttribute(ModelAttributeNameFactory.EVENT_START_TIME, currentEvent.getEventStartTime().toInstant(ZoneOffset.ofHours(9)).toEpochMilli() / 1000);
        model.addAttribute(ModelAttributeNameFactory.EVENT_END_TIME, currentEvent.getEventEndTime().toInstant(ZoneOffset.ofHours(9)).toEpochMilli() / 1000);

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_EVENT_SETTING.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/general", method = RequestMethod.POST)
    public String eventSettingGeneral(EventSettingForm eventSettingForm) {
        Event currentEvent = new Event();
        currentEvent.setEventId(eventSettingForm.getEventId());
        currentEvent.setEventStatus(eventSettingForm.getEventStatus());
        currentEvent.setEventTitle(eventSettingForm.getEventTitle());
        currentEvent.setEventDetail(eventSettingForm.getEventDetail());
        currentEvent.setEventStartTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(eventSettingForm.getEventStartTime()), ZoneOffset.ofHours(9)));
        currentEvent.setEventEndTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(eventSettingForm.getEventEndTime()), ZoneOffset.ofHours(9)));

        eventService.updateEvent(currentEvent);

        return View.ADMIN_EVENT_SETTING.toRedirect();
    }
}
