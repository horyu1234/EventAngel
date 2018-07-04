package com.horyu1234.eventangel.domain;

/**
 * Created by horyu on 2018-04-14
 */
public class CompanyAndPrize {
    private int prizeId;
    private int companyId;
    private String companyName;
    private String companyDetail;
    private String companyLogoImageFileName;
    private String prizeName;
    private int prizeAmount;

    public CompanyAndPrize() {
    }

    public CompanyAndPrize(Company company, Prize prize) {
        this.prizeId = prize.getPrizeId();
        this.companyId = prize.getCompanyId();
        this.companyName = company.getCompanyName();
        this.companyDetail = company.getCompanyDetail();
        this.companyLogoImageFileName = company.getCompanyLogoImageFileName();
        this.prizeName = prize.getPrizeName();
        this.prizeAmount = prize.getPrizeAmount();
    }

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getCompanyDetail() {
        return companyDetail;
    }

    public void setCompanyDetail(String companyDetail) {
        this.companyDetail = companyDetail;
    }

    public String getCompanyLogoImageFileName() {
        return companyLogoImageFileName;
    }

    public void setCompanyLogoImageFileName(String companyLogoImageFileName) {
        this.companyLogoImageFileName = companyLogoImageFileName;
    }

    public int getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(int prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    public CompanyAndPrize copy() {
        CompanyAndPrize companyAndPrize = new CompanyAndPrize();
        companyAndPrize.setPrizeId(this.prizeId);
        companyAndPrize.setCompanyId(this.companyId);
        companyAndPrize.setCompanyName(this.companyName);
        companyAndPrize.setPrizeName(this.prizeName);
        companyAndPrize.setCompanyDetail(this.companyDetail);
        companyAndPrize.setPrizeAmount(this.prizeAmount);

        return companyAndPrize;
    }
}
