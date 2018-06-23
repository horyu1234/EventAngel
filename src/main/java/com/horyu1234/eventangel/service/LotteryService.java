package com.horyu1234.eventangel.service;

import com.horyu1234.eventangel.constant.EventStatus;
import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.Event;
import org.apache.commons.math3.random.MersenneTwister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by horyu on 2018-04-16
 */
@Service
public class LotteryService {
    private EventService eventService;
    private ApplicantService applicantService;
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
    public void setEventWinnerService(EventWinnerService eventWinnerService) {
        this.eventWinnerService = eventWinnerService;
    }

    public Applicant lottery(int prizeId) {
        Event currentEvent = eventService.getCurrentEvent();

        if (currentEvent.getEventStatus() != EventStatus.LOTTERY) {
            currentEvent.setEventStatus(EventStatus.LOTTERY);
            eventService.updateEvent(currentEvent);
        }

        List<Applicant> applicantList = applicantService.getApplicantList(currentEvent.getEventId());
        applicantList = applicantList.stream().filter(applicant -> !applicant.isDuplicated()).collect(Collectors.toList());

        MersenneTwister mersenneTwister = new MersenneTwister();
        int randomIndex = mersenneTwister.nextInt(applicantList.size());

        Applicant lotteryedApplicant = applicantList.get(randomIndex);

        int eventId = currentEvent.getEventId();
        eventWinnerService.addEventWinner(eventId, prizeId, lotteryedApplicant.getApplyEmail());

        return lotteryedApplicant;
    }
}
