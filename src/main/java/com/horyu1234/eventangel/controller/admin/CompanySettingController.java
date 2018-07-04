package com.horyu1234.eventangel.controller.admin;

import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Company;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.form.CompanySaveForm;
import com.horyu1234.eventangel.service.CompanyService;
import com.horyu1234.eventangel.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String saveCompany(CompanySaveForm companySaveForm,
                              @RequestParam("companyLogoFile") MultipartFile multipartFile) {
        Event currentEvent = eventService.getCurrentEvent();

        int eventId = currentEvent.getEventId();
        int companyId = companySaveForm.getCompanyId();

        Company company = new Company();
        company.setEventId(eventId);
        company.setCompanyId(companyId);
        company.setCompanyName(companySaveForm.getCompanyName());
        company.setCompanyDetail(companySaveForm.getCompanyDetail());

        companyService.saveCompany(company);

        if (companyId == 0 && multipartFile.getContentType() != null && multipartFile.getContentType().startsWith("image/")) {
            companyService.saveCompanyLogoImage(eventId, company.getCompanyId(), multipartFile);
        }

        return View.ADMIN_COMPANY_SETTING.toRedirect();
    }
}
