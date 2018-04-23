package com.horyu1234.husuabieventlotteryapply.form;

import javax.validation.constraints.NotNull;

/**
 * Created by horyu on 2018-04-03
 */
public class CheckForm {
    @NotNull
    private String email;

    @NotNull
    private String recaptchaResponse;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecaptchaResponse() {
        return recaptchaResponse;
    }

    public void setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
    }
}
