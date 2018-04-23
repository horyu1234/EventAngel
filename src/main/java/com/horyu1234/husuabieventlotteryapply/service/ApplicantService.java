package com.horyu1234.husuabieventlotteryapply.service;

import com.google.common.collect.Lists;
import com.horyu1234.husuabieventlotteryapply.database.dao.ApplicantDAO;
import com.horyu1234.husuabieventlotteryapply.domain.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by horyu on 2018-04-03
 */
@Service
public class ApplicantService {
    private ApplicantDAO applicantDAO;

    @PostConstruct
    public void init() {
        applicantDAO.createTableIfNotExist();
    }

    @Autowired
    public void setApplicantDAO(ApplicantDAO applicantDAO) {
        this.applicantDAO = applicantDAO;
    }

    public void addApply(Applicant applicant) {
        applicantDAO.insertApplicant(applicant);
    }

    public Applicant getApply(String email) {
        List<Applicant> applicantList = applicantDAO.getApplicant(email);
        return applicantList.isEmpty() ? null : applicantList.get(0);
    }

    public long getApplyCount() {
        return applicantDAO.getApplicantCount();
    }

    public List<Applicant> getRecent50ApplicantList() {
        return Lists.newArrayList(applicantDAO.getRecent50Applicant());
    }

    public List<Applicant> getApplicantList() {
        return Lists.newArrayList(applicantDAO.getApplicant());
    }

    public void truncate() {
        applicantDAO.truncate();
    }
}
