package com.horyu1234.husuabieventlotteryapply.service;

import com.horyu1234.husuabieventlotteryapply.database.dao.CompanyDAO;
import com.horyu1234.husuabieventlotteryapply.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by horyu on 2018-04-06
 */
@Service
public class CompanyService {
    private CompanyDAO companyDAO;

    @PostConstruct
    public void init() {
        companyDAO.createTableIfNotExist();
    }

    @Autowired
    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void saveCompany(Company company) {
        int eventId = company.getEventId();

        if (company.getCompanyId() == 0) {
            int maxCompanyId = 0;

            if (!companyDAO.getCompanyList(eventId).isEmpty()) {
                maxCompanyId = companyDAO.getMaxCompanyId(eventId);
            }

            company.setCompanyId(maxCompanyId + 1);
        }

        companyDAO.updateOrInsertCompany(company);
    }

    public void deleteCompany(int eventId, int companyId) {
        companyDAO.deleteCompany(eventId, companyId);
    }

    public List<Company> getCompanyList(int eventId) {
        return companyDAO.getCompanyList(eventId);
    }
}
