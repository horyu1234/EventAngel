package com.horyu1234.husuabieventlotteryapply.service;

import com.horyu1234.husuabieventlotteryapply.database.dao.EventWinnerDAO;
import com.horyu1234.husuabieventlotteryapply.domain.Applicant;
import com.horyu1234.husuabieventlotteryapply.domain.CompanyAndPrize;
import com.horyu1234.husuabieventlotteryapply.domain.EventWinner;
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

    @PostConstruct
    public void init() {
        eventWinnerDAO.createTableIfNotExist();
    }

    @Autowired
    public void setPrizeService(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @Autowired
    public void setEventWinnerDAO(EventWinnerDAO eventWinnerDAO) {
        this.eventWinnerDAO = eventWinnerDAO;
    }

    public void resetEventWinner(int eventId) {
        eventWinnerDAO.resetEventWinner(eventId);
    }

    public void addEventWinner(int eventId, int prizeId, Applicant applicant) {
        eventWinnerDAO.insertEventWinner(eventId, prizeId, applicant);
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

            String email = applicant.getEmail();

            String emailName = email.split("@")[0].replaceAll("$", "*");
            String emailHost = email.split("@")[1].replaceAll("[a-zA-Z가-힣]", "*");

            applicant.setEmail(emailName + '@' + emailHost);
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
                companyAndPrize.setApplicant(eventWinner.toApplicant());
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
