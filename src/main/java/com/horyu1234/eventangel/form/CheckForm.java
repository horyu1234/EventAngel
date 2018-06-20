package com.horyu1234.eventangel.form;

import javax.validation.constraints.NotNull;

/**
 * Created by horyu on 2018-04-03
 */
public class CheckForm {
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
