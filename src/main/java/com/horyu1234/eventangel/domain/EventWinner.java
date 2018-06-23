package com.horyu1234.eventangel.domain;

/**
 * Created by horyu on 2018-04-16
 */
public class EventWinner {
    private int eventId;
    private int prizeId;
    private String applyEmail;

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

    public String getApplyEmail() {
        return applyEmail;
    }

    public void setApplyEmail(String applyEmail) {
        this.applyEmail = applyEmail;
    }
}
