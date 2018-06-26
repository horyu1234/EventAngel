package com.horyu1234.eventangel.domain;

import java.time.LocalDateTime;

/**
 * Created by horyu on 2018-04-16
 */
public class EventWinner {
    private int eventId;
    private int prizeId;
    private String applyEmail;
    private LocalDateTime winTime;

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

    public LocalDateTime getWinTime() {
        return winTime;
    }

    public void setWinTime(LocalDateTime winTime) {
        this.winTime = winTime;
    }
}
