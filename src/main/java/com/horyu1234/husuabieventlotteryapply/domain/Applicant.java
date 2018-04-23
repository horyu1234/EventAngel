package com.horyu1234.husuabieventlotteryapply.domain;

import java.util.Date;

/**
 * Created by horyu on 2018-04-03
 */
public class Applicant {
    private String email;
    private String youtubeNickname;
    private Date applyTime;
    private String ipAddress;
    private String userAgent;
    private String fingerprint2;

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
