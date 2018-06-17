package com.horyu1234.husuabieventlotteryapply.controller.admin;

import com.horyu1234.husuabieventlotteryapply.constant.EventDetailStatus;
import com.horyu1234.husuabieventlotteryapply.constant.View;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.factory.ModelAttributeNameFactory;
import com.horyu1234.husuabieventlotteryapply.service.ApplicantService;
import com.horyu1234.husuabieventlotteryapply.service.EventService;
import com.horyu1234.husuabieventlotteryapply.service.EventWinnerService;
import com.horyu1234.husuabieventlotteryapply.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by horyu on 2018-04-14
 */
@RequestMapping("/admin/lottery")
@Controller
public class LotteryController {
    private EventService eventService;
    private ApplicantService applicantService;
    private PrizeService prizeService;
    private EventWinnerService eventWinnerService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Autowired
    public void setPrizeService(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @Autowired
    public void setEventWinnerService(EventWinnerService eventWinnerService) {
        this.eventWinnerService = eventWinnerService;
    }

    @RequestMapping("/")
    public String lottery(Model model) {
        Event currentEvent = eventService.getCurrentEvent();
        EventDetailStatus eventDetailStatus = eventService.getEventDetailStatus(currentEvent);

        boolean canStartLottery = true;
        String cantStartMessage = "";
        if (prizeService.getTotalPrizeAmount(currentEvent.getEventId()) > applicantService.getApplyCount()) {
            canStartLottery = false;
            cantStartMessage = "존재하는 상품의 수보다 응모 신청을 한 사람의 수가 적습니다.";
        }

        model.addAttribute("cantApply", eventDetailStatus == EventDetailStatus.ALREADY_END || eventDetailStatus == EventDetailStatus.CLOSE);
        model.addAttribute("canStartLottery", canStartLottery);
        model.addAttribute("cantStartMessage", cantStartMessage);
        model.addAttribute("applyCount", applicantService.getApplyCount());
        model.addAttribute("eventResult", eventWinnerService.getCurrentEventResult(currentEvent.getEventId()));

        model.addAttribute(ModelAttributeNameFactory.VIEW_NAME, View.ADMIN_LOTTERY.toView());

        return View.LAYOUT.getTemplateName();
    }
}
