package com.horyu1234.husuabieventlotteryapply.controller.admin;

import com.horyu1234.husuabieventlotteryapply.constant.View;
import com.horyu1234.husuabieventlotteryapply.domain.Company;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.factory.ModelAttributeNameFactory;
import com.horyu1234.husuabieventlotteryapply.form.CompanySaveForm;
import com.horyu1234.husuabieventlotteryapply.service.CompanyService;
import com.horyu1234.husuabieventlotteryapply.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by horyu on 2018-04-04
 */
@RequestMapping("/admin/companySetting")
@Controller
public class CompanySettingController {
    private EventService eventService;
    private CompanyService companyService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String companySetting(Model model) {
        Event currentEvent = eventService.getCurrentEvent();

        model.addAttribute("companyList", companyService.getCompanyList(currentEvent.getEventId()));
        model.addAttribute(ModelAttributeNameFactory.EVENT_STATUS, currentEvent.getEventStatus());

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_COMPANY_SETTING.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
    public String saveCompany(CompanySaveForm companySaveForm) {
        Event currentEvent = eventService.getCurrentEvent();

        Company company = new Company();
        company.setEventId(currentEvent.getEventId());
        company.setCompanyId(companySaveForm.getCompanyId());
        company.setCompanyName(companySaveForm.getCompanyName());
        company.setCompanyDetail(companySaveForm.getCompanyDetail());

        companyService.saveCompany(company);

        return View.ADMIN_COMPANY_SETTING.toRedirect();
    }
}
