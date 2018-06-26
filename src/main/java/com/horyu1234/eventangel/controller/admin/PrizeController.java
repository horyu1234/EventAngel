package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.domain.Prize;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.form.PrizeSaveForm;
import com.horyu1234.eventangel.service.CompanyService;
import com.horyu1234.eventangel.service.EventService;
import com.horyu1234.eventangel.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by horyu on 2018-04-04
 */
@RequestMapping("/admin/prizeSetting")
@Controller
public class PrizeController {
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String prizeSetting(Model model) {
        Event currentEvent = eventService.getCurrentEvent();

        model.addAttribute(ModelAttributeNameFactory.EVENT_STATUS, currentEvent.getEventStatus());
        model.addAttribute("companyList", companyService.getCompanyList(currentEvent.getEventId()));
        model.addAttribute("prizeList", prizeService.getCompanyGiftData(currentEvent.getEventId()));

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_PRIZE_SETTING.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/savePrize", method = RequestMethod.POST)
    public String savePrize(PrizeSaveForm prizeSaveForm) {
        Event currentEvent = eventService.getCurrentEvent();

        Prize prize = new Prize();
        prize.setEventId(currentEvent.getEventId());
        prize.setPrizeId(prizeSaveForm.getPrizeId());
        prize.setCompanyId(prizeSaveForm.getCompanyId());
        prize.setPrizeName(prizeSaveForm.getPrizeName());
        prize.setPrizeAmount(prizeSaveForm.getPrizeAmount());

        prizeService.savePrize(prize);

        return View.ADMIN_PRIZE_SETTING.toRedirect();
    }
}
