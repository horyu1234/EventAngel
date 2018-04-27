package com.horyu1234.husuabieventlotteryapply.controller.admin;

import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.ViewNames;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.form.EventSettingForm;
import com.horyu1234.husuabieventlotteryapply.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by horyu on 2018-04-04
 */
@RequestMapping("/admin/eventSetting")
@Controller
public class EventSettingController {
    private static final String VIEW_ADMIN_EVENT_SETTING = "view/admin/eventSetting";
    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String eventSetting(Model model) {
        Event currentEvent = eventService.getCurrentEvent();
        model.addAttribute(ModelAttributeNames.EVENT_ID, currentEvent.getEventId());
        model.addAttribute(ModelAttributeNames.EVENT_STATUS, currentEvent.getEventStatus());
        model.addAttribute(ModelAttributeNames.EVENT_TITLE, currentEvent.getEventTitle());
        model.addAttribute(ModelAttributeNames.EVENT_DETAIL, currentEvent.getEventDetail());
        model.addAttribute(ModelAttributeNames.EVENT_START_TIME, currentEvent.getEventStartTime());
        model.addAttribute(ModelAttributeNames.EVENT_END_TIME, currentEvent.getEventEndTime());

        model.addAttribute(ModelAttributeNames.VIEW_NAME, VIEW_ADMIN_EVENT_SETTING);

        return ViewNames.LAYOUT;
    }

    @RequestMapping(value = "/general", method = RequestMethod.POST)
    public String eventSettingGeneral(Model model, EventSettingForm eventSettingForm) {
        Event currentEvent = new Event();
        currentEvent.setEventId(eventSettingForm.getEventId());
        currentEvent.setEventStatus(eventSettingForm.getEventStatus());
        currentEvent.setEventTitle(eventSettingForm.getEventTitle());
        currentEvent.setEventDetail(eventSettingForm.getEventDetail());
        currentEvent.setEventStartTime(eventSettingForm.getEventStartTime());
        currentEvent.setEventEndTime(eventSettingForm.getEventEndTime());

        eventService.updateEvent(currentEvent);

        return "redirect:/admin/eventSetting/";
    }
}
