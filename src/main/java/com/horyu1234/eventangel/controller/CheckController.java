package com.horyu1234.eventangel.controller;

import com.horyu1234.eventangel.constant.EventDetailStatus;
import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.factory.DateFactory;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.form.CheckForm;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
import com.horyu1234.eventangel.service.ReCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by horyu on 2018-04-02
 */
@Controller
public class CheckController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckController.class);
    private ReCaptchaService reCaptchaService;
    private EventService eventService;
    private ApplicantService applicantService;

    @Autowired
    public CheckController(ReCaptchaService reCaptchaService, EventService eventService, ApplicantService applicantService) {
        this.reCaptchaService = reCaptchaService;
        this.eventService = eventService;
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(Model model) {
        Event currentEvent = eventService.getCurrentEvent();
        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        if (eventDetailStatus != EventDetailStatus.OPEN) {
            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.CHECK_NOT_OPEN_EVENT.toView());

            return View.LAYOUT.getTemplateName();
        }

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.CHECK_CHECK.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkReceive(Model model, CheckForm checkForm,
                               @RequestParam(name = "g-recaptcha-response") String reCaptchaResponse) {
        Event currentEvent = eventService.getCurrentEvent();
        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        String clientIpAddress = getClientIpAddress();
        if (!reCaptchaService.verifyReCaptcha(clientIpAddress, reCaptchaResponse)) {
            LOGGER.info("[{}] ReCaptcha 인증에 실패하였습니다. {}", clientIpAddress, checkForm.getEmail());

            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.FAIL_RECAPTCHA.toView());

            return View.LAYOUT.getTemplateName();
        }

        if (eventDetailStatus != EventDetailStatus.OPEN) {
            LOGGER.info("[{}] 응모 중이 아닐 때 응모 확인을 시도하였습니다. {}", clientIpAddress, checkForm.getEmail());

            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.CHECK_NOT_OPEN_EVENT.toView());

            return View.LAYOUT.getTemplateName();
        }

        Applicant storedApplicant = applicantService.getApply(currentEvent.getEventId(), checkForm.getEmail());
        if (storedApplicant != null) {
            model.addAttribute("applyTime", DateFactory.PRETTY_FORMAT.format(storedApplicant.getApplyTime()));
            model.addAttribute("youtubeNickname", storedApplicant.getYoutubeNickname());
            model.addAttribute("applyEmail", storedApplicant.getApplyEmail());

            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.CHECK_EXIST_APPLY.toView());

            return View.LAYOUT.getTemplateName();
        }

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.CHECK_NOT_EXIST_APPLY.toView());

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
