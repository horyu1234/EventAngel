package com.horyu1234.eventangel.form;

import javax.validation.constraints.NotNull;

/**
 * Created by horyu on 2018-04-14
 */
public class PrizeSaveForm {
    @NotNull
    private int prizeId;

    @NotNull
    private int companyId;

    @NotNull
    private String prizeName;

    @NotNull
    private int prizeAmount;

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public int getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(int prizeAmount) {
        this.prizeAmount = prizeAmount;
    }
}
