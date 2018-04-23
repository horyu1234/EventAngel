package com.horyu1234.husuabieventlotteryapply.form;

import javax.validation.constraints.NotNull;

/**
 * Created by horyu on 2018-04-03
 */
public class LoginForm {
    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
