package com.horyu1234.eventangel.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Created by horyu on 2018-04-03
 */
public class ApplyForm {
    @NotNull
    private String youtubeNickname;

    @NotNull
    private String email;

    @Null
    private String fingerprint2;

    public String getYoutubeNickname() {
        return youtubeNickname;
    }

    public void setYoutubeNickname(String youtubeNickname) {
        this.youtubeNickname = youtubeNickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFingerprint2() {
        return fingerprint2;
    }

    public void setFingerprint2(String fingerprint2) {
        this.fingerprint2 = fingerprint2;
    }
}
