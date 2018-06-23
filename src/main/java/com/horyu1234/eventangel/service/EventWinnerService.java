package com.horyu1234.eventangel.service;

import com.horyu1234.eventangel.database.dao.EventWinnerDAO;
import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.CompanyAndPrize;
import com.horyu1234.eventangel.domain.EventWinner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by horyu on 2018-04-15
 */
@Service
public class EventWinnerService {
    private EventWinnerDAO eventWinnerDAO;
    private PrizeService prizeService;
    private ApplicantService applicantService;

    @Autowired
    public EventWinnerService(EventWinnerDAO eventWinnerDAO, PrizeService prizeService, ApplicantService applicantService) {
        this.eventWinnerDAO = eventWinnerDAO;
        this.prizeService = prizeService;
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

    public List<EventWinner> getWinnerList(int eventId) {
        return eventWinnerDAO.getWinnerList(eventId);
    }

    public List<CompanyAndPrize> hidePrivacy(List<CompanyAndPrize> eventResult) {
        return eventResult.stream().map(companyAndPrize -> {
            Applicant applicant = companyAndPrize.getApplicant();
            if (applicant == null) {
                return companyAndPrize;
            }

            String email = applicant.getApplyEmail();

            String originalEmailName = email.split("@")[0];
            String emailName = originalEmailName.substring(0, originalEmailName.length() - 1) + "*";
            String emailHost = email.split("@")[1].replaceAll("[a-zA-Z가-힣0-9\\-]", "*");

            applicant.setApplyEmail(emailName + '@' + emailHost);
            applicant.setApplyTime(null);
            applicant.setIpAddress("HIDDEN");
            applicant.setUserAgent("HIDDEN");
            applicant.setFingerprint2("HIDDEN");

            companyAndPrize.setApplicant(applicant);

            return companyAndPrize;
        }).collect(Collectors.toList());
    }

    public List<CompanyAndPrize> getCurrentEventResult(int eventId) {
        List<CompanyAndPrize> prizeList = prizeService.getPrizeList(eventId);
        prizeList = appendPrizeWithPrizeAmount(prizeList);

        List<EventWinner> winnerList = getWinnerList(eventId);
        return prizeList.stream().peek(companyAndPrize -> {
            EventWinner eventWinner = popWinner(winnerList, eventId, companyAndPrize.getPrizeId());

            if (eventWinner != null) {
                Applicant apply = applicantService.getApply(eventId, eventWinner.getApplyEmail());

                companyAndPrize.setApplicant(apply);
            }
        }).collect(Collectors.toList());
    }

    private List<CompanyAndPrize> appendPrizeWithPrizeAmount(List<CompanyAndPrize> prizeList) {
        List<CompanyAndPrize> newList = new ArrayList<>();

        for (CompanyAndPrize companyAndPrize : prizeList) {
            int prizeAmount = companyAndPrize.getPrizeAmount();

            for (int i = 0; i < prizeAmount; i++) {
                newList.add(companyAndPrize.copy());
            }
        }

        return newList;
    }

    private EventWinner popWinner(List<EventWinner> winnerList, int eventId, int prizeId) {
        for (EventWinner eventWinner : winnerList) {
            if (eventWinner.getEventId() == eventId && eventWinner.getPrizeId() == prizeId) {
                winnerList.remove(eventWinner);
                return eventWinner;
            }
        }

        return null;
    }
}
