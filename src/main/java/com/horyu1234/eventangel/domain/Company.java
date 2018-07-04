package com.horyu1234.eventangel.domain;

/**
 * Created by horyu on 2018-04-06
 */
public class Company {
    private int eventId;
    private int companyId;
    private String companyName;
    private String companyDetail;
    private String companyLogoImageFileName;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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
}
