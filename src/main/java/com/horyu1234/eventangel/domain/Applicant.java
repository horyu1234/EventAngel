package com.horyu1234.eventangel.domain;

import java.time.LocalDateTime;

/**
 * Created by horyu on 2018-04-03
 */
public class Applicant {
    private int eventId;
    private String applyEmail;
    private String youtubeNickname;
    private LocalDateTime applyTime;
    private String ipAddress;
    private String userAgent;
    private String fingerprint2;
    private boolean duplicated;
    private String dupCheckAdminName;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyEmail() {
        return applyEmail;
    }

    public void setApplyEmail(String applyEmail) {
        this.applyEmail = applyEmail;
    }

    public String getYoutubeNickname() {
        return youtubeNickname;
    }

    public void setYoutubeNickname(String youtubeNickname) {
        this.youtubeNickname = youtubeNickname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getFingerprint2() {
        return fingerprint2;
    }

    public void setFingerprint2(String fingerprint2) {
        this.fingerprint2 = fingerprint2;
    }

    public boolean isDuplicated() {
        return duplicated;
    }

    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }

    public String getDupCheckAdminName() {
        return dupCheckAdminName;
    }

    public void setDupCheckAdminName(String dupCheckAdminName) {
        this.dupCheckAdminName = dupCheckAdminName;
    }
}
