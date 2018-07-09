package com.horyu1234.eventangel.controller;

import com.horyu1234.eventangel.constant.EventDetailStatus;
import com.horyu1234.eventangel.constant.View;
import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.CompanyAndPrize;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.domain.EventWinnerDetail;
import com.horyu1234.eventangel.factory.DateFactory;
import com.horyu1234.eventangel.factory.ModelAttributeNameFactory;
import com.horyu1234.eventangel.form.ApplyForm;
import com.horyu1234.eventangel.service.ApplicantService;
import com.horyu1234.eventangel.service.EventService;
import com.horyu1234.eventangel.service.EventWinnerService;
import com.horyu1234.eventangel.service.PrizeService;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by horyu on 2018-04-02
 */
@Controller
public class ApplyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyController.class);
    private ApplicantService applicantService;
    private EventService eventService;
    private PrizeService prizeService;
    private EventWinnerService eventWinnerService;

    @Autowired
    public ApplyController(ApplicantService applicantService, EventService eventService, PrizeService prizeService, EventWinnerService eventWinnerService) {
        this.applicantService = applicantService;
        this.eventService = eventService;
        this.prizeService = prizeService;
        this.eventWinnerService = eventWinnerService;
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public String apply(Model model) {
        Event currentEvent = eventService.getCurrentEvent();

        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        model.addAttribute(ModelAttributeNameFactory.EVENT_TITLE, currentEvent.getEventTitle());
        model.addAttribute(ModelAttributeNameFactory.EVENT_DETAIL, currentEvent.getEventDetail());
        model.addAttribute(ModelAttributeNameFactory.EVENT_STATUS, eventDetailStatus);
        model.addAttribute(ModelAttributeNameFactory.EVENT_START_TIME, currentEvent.getEventStartTime().toInstant(ZoneOffset.ofHours(9)).toEpochMilli() / 1000);
        model.addAttribute(ModelAttributeNameFactory.EVENT_END_TIME, currentEvent.getEventEndTime().toInstant(ZoneOffset.ofHours(9)).toEpochMilli() / 1000);

        model.addAttribute("applyCount", applicantService.getApplyCount(currentEvent.getEventId(), true));
        model.addAttribute("dupApplyCount", applicantService.getApplyCount(currentEvent.getEventId(), getClientIpAddress()));

        List<CompanyAndPrize> companyGiftData = new ArrayList<>();
        List<EventWinnerDetail> winnerList = new ArrayList<>();

        if (eventDetailStatus == EventDetailStatus.OPEN || eventDetailStatus == EventDetailStatus.LOTTERY) {
            companyGiftData = prizeService.getCompanyGiftData(currentEvent.getEventId());
        }

        if (eventDetailStatus == EventDetailStatus.LOTTERY) {
            winnerList = eventWinnerService.getWinnerList(currentEvent.getEventId());
            winnerList = eventWinnerService.hidePrivacy(winnerList);
        }

        model.addAttribute("companyGiftData", companyGiftData);
        model.addAttribute("eventWinnerData", winnerList);

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.APPLY_APPLY.toView());

        return View.LAYOUT.getTemplateName();
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public String applyReceive(Model model, ApplyForm applyForm,
                               @RequestHeader(value = "User-Agent", required = false) String useragent) {
        Event currentEvent = eventService.getCurrentEvent();

        Applicant storedApplicant = applicantService.getApply(currentEvent.getEventId(), applyForm.getEmail());
        if (storedApplicant != null) {
            model.addAttribute("applyTime", DateFactory.PRETTY_FORMAT.format(storedApplicant.getApplyTime()));
            model.addAttribute("youtubeNickname", storedApplicant.getYoutubeNickname());
            model.addAttribute("applyEmail", storedApplicant.getApplyEmail());
            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.APPLY_REDUPLICATION.toView());

            return View.LAYOUT.getTemplateName();
        }

        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        if (eventDetailStatus == EventDetailStatus.START_SOON) {
            LOGGER.info("[{}] 곧 시작될 이벤트에 응모를 시도하였습니다. {} ({})", getClientIpAddress(), applyForm.getEmail(), applyForm.getYoutubeNickname());

            return View.APPLY.toRedirect();
        } else if (eventDetailStatus == EventDetailStatus.CLOSE || eventDetailStatus == EventDetailStatus.LOTTERY) {
            LOGGER.info("[{}] 비활성화된 이벤트에 응모를 시도하였습니다. {} ({})", getClientIpAddress(), applyForm.getEmail(), applyForm.getYoutubeNickname());

            return View.APPLY.toRedirect();
        } else if (eventDetailStatus == EventDetailStatus.ALREADY_END) {
            LOGGER.info("[{}] 이미 기간이 종료된 이벤트에 응모를 시도하였습니다. {} ({})", getClientIpAddress(), applyForm.getEmail(), applyForm.getYoutubeNickname());

            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.APPLY_ALREADY_END.toView());

            return View.LAYOUT.getTemplateName();
        }

        long dupApplyCount = applicantService.getApplyCount(currentEvent.getEventId(), getClientIpAddress());
        if (dupApplyCount >= 5) {
            LOGGER.info("[{}] 5번 초과로 응모를 시도하였습니다. {} ({})", getClientIpAddress(), applyForm.getEmail(), applyForm.getYoutubeNickname());

            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.APPLY_MAX_COUNT.toView());

            return View.LAYOUT.getTemplateName();
        }

        Applicant applicant = new Applicant();
        applicant.setEventId(currentEvent.getEventId());
        applicant.setApplyTime(LocalDateTime.now());
        applicant.setApplyEmail(applyForm.getEmail());
        applicant.setYoutubeNickname(applyForm.getYoutubeNickname());
        applicant.setIpAddress(getClientIpAddress());
        applicant.setReferer(getReferer());
        applicant.setUserAgent(useragent);
        applicant.setFingerprint2(StringUtils.isNullOrEmpty(applyForm.getFingerprint2()) ? null : applyForm.getFingerprint2());

        if (StringUtils.isNullOrEmpty(applicant.getApplyEmail()) || StringUtils.isNullOrEmpty(applicant.getYoutubeNickname()) || !applicant.getApplyEmail().contains("@")) {
            model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.APPLY_INVALID_FORM.toView());

            return View.LAYOUT.getTemplateName();
        }

        applicantService.addApply(applicant);

        model.addAttribute("applyTime", DateFactory.PRETTY_FORMAT.format(applicant.getApplyTime()));
        model.addAttribute("youtubeNickname", applicant.getYoutubeNickname());
        model.addAttribute("applyEmail", applicant.getApplyEmail());
        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.APPLY_SUCCESS.toView());

        LOGGER.info("[{}] 새로운 응모 - {} ({})", getClientIpAddress(), applicant.getApplyEmail(), applicant.getYoutubeNickname());

        return View.LAYOUT.getTemplateName();
    }

    private String getClientIpAddress() {
        HttpServletRequest req = getCurrentServletRequest();

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

    private String getReferer() {
        HttpServletRequest req = getCurrentServletRequest();
        return req.getHeader("Referer");
    }

    private HttpServletRequest getCurrentServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
