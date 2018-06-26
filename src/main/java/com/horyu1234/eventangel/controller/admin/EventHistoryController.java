package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.service.EventService;
import com.horyu1234.eventangel.service.EventWinnerService;
import com.horyu1234.eventangel.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by horyu on 2018-04-16
 */
@RequestMapping("/admin/eventHistory")
@Controller
public class EventHistoryController {
    private EventService eventService;
    private PrizeService prizeService;
    private EventWinnerService eventWinnerService;

    @Autowired
    public EventHistoryController(EventService eventService, PrizeService prizeService, EventWinnerService eventWinnerService) {
        this.eventService = eventService;
        this.prizeService = prizeService;
        this.eventWinnerService = eventWinnerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String eventHistory(Model model) {
        model.addAttribute("eventList", eventService.getEventList());
        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_EVENT_HISTORY.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/view/{eventId}", method = RequestMethod.GET)
    public String eventHistory(Model model, @PathVariable int eventId) {
        model.addAttribute("companyGiftData", prizeService.getCompanyGiftData(eventId));
        model.addAttribute("eventWinnerData", eventWinnerService.getWinnerList(eventId));

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_EVENT_HISTORY_VIEW.toView());

        return View.LAYOUT.getTemplateName();
    }
}
