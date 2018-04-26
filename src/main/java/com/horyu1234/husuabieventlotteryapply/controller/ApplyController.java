package com.horyu1234.husuabieventlotteryapply.controller;

import com.horyu1234.husuabieventlotteryapply.constant.EventDetailStatus;
import com.horyu1234.husuabieventlotteryapply.constant.ModelAttributeNames;
import com.horyu1234.husuabieventlotteryapply.constant.ViewNames;
import com.horyu1234.husuabieventlotteryapply.domain.Applicant;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.form.ApplyForm;
import com.horyu1234.husuabieventlotteryapply.service.ApplicantService;
import com.horyu1234.husuabieventlotteryapply.service.EventService;
import com.horyu1234.husuabieventlotteryapply.service.PrizeService;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by horyu on 2018-04-02
 */
@Controller
public class ApplyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyController.class);
    private ApplicantService applicantService;
    private EventService eventService;
    private PrizeService prizeService;

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setPrizeService(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public String apply(Model model) {
        model.addAttribute("applyCount", applicantService.getApplyCount());

        Event currentEvent = eventService.getCurrentEvent();
        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        model.addAttribute(ModelAttributeNames.EVENT_TITLE, currentEvent.getEventTitle());
        model.addAttribute(ModelAttributeNames.EVENT_DETAIL, currentEvent.getEventDetail());
        model.addAttribute(ModelAttributeNames.EVENT_STATUS, eventDetailStatus);
        model.addAttribute(ModelAttributeNames.EVENT_START_TIME, currentEvent.getEventStartTime());
        model.addAttribute(ModelAttributeNames.EVENT_END_TIME, currentEvent.getEventEndTime());

        model.addAttribute("prizeList", prizeService.getPrizeList(currentEvent.getEventId()));

        model.addAttribute(ModelAttributeNames.VIEW_NAME, "view/apply/apply");

        return ViewNames.LAYOUT;
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public String applyReceive(Model model, ApplyForm applyForm,
                               @RequestHeader(value = "User-Agent", required = false) String useragent) {
        Applicant storedApplicant = applicantService.getApply(applyForm.getEmail());
        if (storedApplicant != null) {
            model.addAttribute("applicant", storedApplicant);
            model.addAttribute(ModelAttributeNames.VIEW_NAME, "view/apply/reduplication");

            return ViewNames.LAYOUT;
        }

        Event currentEvent = eventService.getCurrentEvent();
        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        if (eventDetailStatus == EventDetailStatus.START_SOON) {
            String applyBeforeEventMessage = String.format("[%s] 곧 시작될 이벤트에 응모를 시도하였습니다.", getClientIpAddress());
            LOGGER.info(applyBeforeEventMessage);

            return "redirect:/apply";
        } else if (eventDetailStatus == EventDetailStatus.CLOSE) {
            String applyDeActiveEventMessage = String.format("[%s] 비활성화된 이벤트에 응모를 시도하였습니다.", getClientIpAddress());
            LOGGER.info(applyDeActiveEventMessage);

            return "redirect:/apply";
        } else if (eventDetailStatus == EventDetailStatus.ALREADY_END) {
            String applyEndEventMessage = String.format("[%s] 이미 기간이 종료된 이벤트에 응모를 시도하였습니다.", getClientIpAddress());
            LOGGER.info(applyEndEventMessage);

            model.addAttribute(ModelAttributeNames.VIEW_NAME, "view/apply/alreadyEnd");

            return ViewNames.LAYOUT;
        }

        Applicant applicant = new Applicant();
        applicant.setApplyTime(new Date());
        applicant.setEmail(applyForm.getEmail());
        applicant.setYoutubeNickname(applyForm.getYoutubeNickname());
        applicant.setIpAddress(getClientIpAddress());
        applicant.setUserAgent(useragent);
        applicant.setFingerprint2(StringUtils.isNullOrEmpty(applyForm.getFingerprint2()) ? null : applyForm.getFingerprint2());

        if (StringUtils.isNullOrEmpty(applicant.getEmail()) || StringUtils.isNullOrEmpty(applicant.getYoutubeNickname()) || !applicant.getEmail().contains("@")) {
            model.addAttribute(ModelAttributeNames.VIEW_NAME, "view/apply/invalidForm");

            return ViewNames.LAYOUT;
        }

        applicantService.addApply(applicant);

        model.addAttribute("applicant", applicant);
        model.addAttribute(ModelAttributeNames.VIEW_NAME, "view/apply/success");


        String newApply = String.format("[%s] 새로운 응모 - %s / %s", getClientIpAddress(), applicant.getEmail(), applicant.getYoutubeNickname());
        LOGGER.info(newApply);

        return ViewNames.LAYOUT;
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
