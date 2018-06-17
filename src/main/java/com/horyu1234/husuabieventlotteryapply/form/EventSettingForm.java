package com.horyu1234.husuabieventlotteryapply.form;

import com.horyu1234.husuabieventlotteryapply.constant.EventStatus;

import javax.validation.constraints.NotNull;

/**
 * Created by horyu on 2018-04-06
 */
public class EventSettingForm {
    @NotNull
    private int eventId;

    @NotNull
    private String eventTitle;

    @NotNull
    private String eventDetail;

    @NotNull
    private EventStatus eventStatus;

    @NotNull
    private long eventStartTime;

    @NotNull
    private long eventEndTime;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public long getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(long eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public long getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(long eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }
}
