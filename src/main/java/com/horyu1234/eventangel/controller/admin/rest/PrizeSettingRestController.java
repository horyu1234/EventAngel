package com.horyu1234.eventangel.controller.admin.rest;

import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.service.EventService;
import com.horyu1234.eventangel.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by horyu on 2018-04-04
 */
@RequestMapping("/admin/prizeSetting")
@RestController
public class PrizeSettingRestController {
    private EventService eventService;
    private PrizeService prizeService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setPrizeService(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @RequestMapping(value = "/deletePrize", method = RequestMethod.POST)
    public String deletePrize(int prizeId) {
        Event currentEvent = eventService.getCurrentEvent();

        prizeService.deletePrize(currentEvent.getEventId(), prizeId);

        return "success";
    }
}
