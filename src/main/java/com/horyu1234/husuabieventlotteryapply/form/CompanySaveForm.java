package com.horyu1234.husuabieventlotteryapply.form;

import javax.validation.constraints.NotNull;

/**
 * Created by horyu on 2018-04-13
 */
public class CompanySaveForm {
    @NotNull
    private int companyId;

    @NotNull
    private String companyName;

    @NotNull
    private String companyDetail;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDetail() {
        return companyDetail;
    }

    public void setCompanyDetail(String companyDetail) {
        this.companyDetail = companyDetail;
    }
}
