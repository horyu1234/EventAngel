package com.horyu1234.eventangel.domain;

/**
 * Created by horyu on 2018-04-16
 */
public class EventWinnerDetail {
    private int eventId;
    private int prizeId;
    private Applicant applicant;

    public static EventWinnerDetail fromEventWinner(EventWinner eventWinner) {
        EventWinnerDetail eventWinnerDetail = new EventWinnerDetail();
        eventWinnerDetail.setEventId(eventWinner.getEventId());
        eventWinnerDetail.setPrizeId(eventWinner.getPrizeId());

        return eventWinnerDetail;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
