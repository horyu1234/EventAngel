package com.horyu1234.husuabieventlotteryapply.controller.admin.rest;

import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.service.CompanyService;
import com.horyu1234.husuabieventlotteryapply.service.EventService;
import com.horyu1234.husuabieventlotteryapply.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by horyu on 2018-04-04
 */
@RequestMapping("/admin/companySetting")
@RestController
public class CompanySettingRestController {
    private EventService eventService;
    private CompanyService companyService;
    private PrizeService prizeService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setPrizeService(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @RequestMapping(value = "/deleteCompany", method = RequestMethod.POST)
    public String deleteCompany(int companyId) {
        Event currentEvent = eventService.getCurrentEvent();

        prizeService.deletePrizeByCompanyId(currentEvent.getEventId(), companyId);
        companyService.deleteCompany(currentEvent.getEventId(), companyId);

        return "success";
    }
}
