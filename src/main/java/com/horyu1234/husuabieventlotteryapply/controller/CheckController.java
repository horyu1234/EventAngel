package com.horyu1234.husuabieventlotteryapply.controller;

import com.horyu1234.husuabieventlotteryapply.constant.EventDetailStatus;
import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.View;
import com.horyu1234.husuabieventlotteryapply.domain.Applicant;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.form.CheckForm;
import com.horyu1234.husuabieventlotteryapply.service.ApplicantService;
import com.horyu1234.husuabieventlotteryapply.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by horyu on 2018-04-02
 */
@Controller
public class CheckController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckController.class);
    private EventService eventService;
    private ApplicantService applicantService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(Model model) {
        Event currentEvent = eventService.getCurrentEvent();
        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        if (eventDetailStatus != EventDetailStatus.OPEN) {
            model.addAttribute(ModelAttributeNames.VIEW_NAME, View.CHECK_NOT_OPEN_EVENT.toView());

            return View.LAYOUT.getTemplateName();
        }

        model.addAttribute(ModelAttributeNames.VIEW_NAME, View.CHECK_CHECK.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkReceive(Model model, CheckForm checkForm) {
        Event currentEvent = eventService.getCurrentEvent();
        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        if (eventDetailStatus != EventDetailStatus.OPEN) {
            LOGGER.info(String.format("[%s] 응모 중이 아닐 때 응모 확인을 시도하였습니다.", getClientIpAddress()));

            model.addAttribute(ModelAttributeNames.VIEW_NAME, View.CHECK_NOT_OPEN_EVENT.toView());

            return View.LAYOUT.getTemplateName();
        }

        Applicant storedApplicant = applicantService.getApply(checkForm.getEmail());
        if (storedApplicant != null) {
            model.addAttribute("applicant", storedApplicant);
            model.addAttribute(ModelAttributeNames.VIEW_NAME, View.CHECK_EXIST_APPLY.toView());

            return View.LAYOUT.getTemplateName();
        }

        model.addAttribute(ModelAttributeNames.VIEW_NAME, View.CHECK_NOT_EXIST_APPLY.toView());

        return View.LAYOUT.getTemplateName();
    }

    private String getClientIpAddress() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip;
        if (req.getHeader("HTTP_CF_CONNECTING_IP") != null) {
            ip = req.getHeader("HTTP_CF_CONNECTING_IP");
        } else if (req.getHeader("X-FORWARDED-FOR") != null) {
            ip = req.getHeader("X-FORWARDED-FOR");
        } else {
            ip = req.getRemoteAddr();
        }

        return ip;
    }
}
