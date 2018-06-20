package com.horyu1234.eventangel.domain;

import java.util.Date;

/**
 * Created by horyu on 2018-04-16
 */
public class EventWinner {
    private int eventId;
    private int prizeId;
    private String applyEmail;
    private String applyYoutubeNickname;
    private Date applyTime;
    private String applyIpAddress;
    private String applyUserAgent;
    private String applyFingerprint2;

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

    public String getApplyYoutubeNickname() {
        return applyYoutubeNickname;
    }

    public void setApplyYoutubeNickname(String applyYoutubeNickname) {
        this.applyYoutubeNickname = applyYoutubeNickname;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyIpAddress() {
        return applyIpAddress;
    }

    public void setApplyIpAddress(String applyIpAddress) {
        this.applyIpAddress = applyIpAddress;
    }

    public String getApplyUserAgent() {
        return applyUserAgent;
    }

    public void setApplyUserAgent(String applyUserAgent) {
        this.applyUserAgent = applyUserAgent;
    }

    public String getApplyFingerprint2() {
        return applyFingerprint2;
    }

    public void setApplyFingerprint2(String applyFingerprint2) {
        this.applyFingerprint2 = applyFingerprint2;
    }

    public Applicant toApplicant() {
        Applicant applicant = new Applicant();
        applicant.setEmail(this.getApplyEmail());
        applicant.setYoutubeNickname(this.getApplyYoutubeNickname());
        applicant.setApplyTime(this.getApplyTime());
        applicant.setIpAddress(this.getApplyIpAddress());
        applicant.setUserAgent(this.getApplyUserAgent());
        applicant.setFingerprint2(this.getApplyFingerprint2());

        return applicant;
    }
}
