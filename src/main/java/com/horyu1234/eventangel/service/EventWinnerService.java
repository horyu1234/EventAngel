package com.horyu1234.eventangel.service;

import com.horyu1234.eventangel.database.dao.EventWinnerDAO;
import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.EventWinner;
import com.horyu1234.eventangel.domain.EventWinnerDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by horyu on 2018-04-15
 */
@Service
public class EventWinnerService {
    private EventWinnerDAO eventWinnerDAO;
    private ApplicantService applicantService;

    @Autowired
    public EventWinnerService(EventWinnerDAO eventWinnerDAO, ApplicantService applicantService) {
        this.eventWinnerDAO = eventWinnerDAO;
        this.applicantService = applicantService;
    }

    @PostConstruct
    public void init() {
        eventWinnerDAO.createTableIfNotExist();
    }

    public void resetEventWinner(int eventId) {
        eventWinnerDAO.resetEventWinner(eventId);
    }

    public void addEventWinner(int eventId, int prizeId, String applyEmail) {
        eventWinnerDAO.insertEventWinner(eventId, prizeId, applyEmail);
    }

    public List<EventWinnerDetail> getWinnerList(int eventId) {
        List<EventWinner> winnerList = eventWinnerDAO.getWinnerList(eventId);

        return winnerList.stream()
                .map(winner -> {
                    Applicant applicant = applicantService.getApply(eventId, winner.getApplyEmail());

                    EventWinnerDetail eventWinnerDetail = EventWinnerDetail.fromEventWinner(winner);
                    eventWinnerDetail.setApplicant(applicant);

                    return eventWinnerDetail;
                })
                .collect(Collectors.toList());
    }

    public List<EventWinnerDetail> hidePrivacy(List<EventWinnerDetail> eventWinnerDetails) {
        return eventWinnerDetails.stream()
                .peek(eventWinnerDetail -> {
                    Applicant applicant = eventWinnerDetail.getApplicant();
                    applicant.hidePrivacy();

                    eventWinnerDetail.setApplicant(applicant);
                }).collect(Collectors.toList());
    }
}
