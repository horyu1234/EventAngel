package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
import com.horyu1234.eventangel.service.EventWinnerService;
import com.horyu1234.eventangel.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by horyu on 2018-04-16
 */
@RequestMapping("/admin/eventHistory")
@Controller
public class EventHistoryController {
    private EventService eventService;
    private PrizeService prizeService;
    private EventWinnerService eventWinnerService;
    private ApplicantService applicantService;

    @Autowired
    public EventHistoryController(EventService eventService, PrizeService prizeService, EventWinnerService eventWinnerService, ApplicantService applicantService) {
        this.eventService = eventService;
        this.prizeService = prizeService;
        this.eventWinnerService = eventWinnerService;
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String eventHistory(Model model) {
        List<Event> eventList = eventService.getEventList();
        Map<Integer, Long> applicantCountMap = new HashMap<>();

        for (Event event : eventList) {
            int eventId = event.getEventId();

            long applyCount = applicantService.getApplyCount(eventId, true);
            applicantCountMap.put(eventId, applyCount);
        }

        model.addAttribute("eventList", eventList);
        model.addAttribute("applicantCountMap", applicantCountMap);

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
