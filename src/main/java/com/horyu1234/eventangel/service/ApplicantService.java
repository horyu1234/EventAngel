package com.horyu1234.eventangel.service;

import com.google.common.collect.Lists;
import com.horyu1234.eventangel.database.dao.ApplicantDAO;
import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.domain.DuplicatedApplicant;
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

    public Applicant getApply(int eventId, String email) {
        List<Applicant> applicantList = applicantDAO.getApplicant(eventId, email);
        return applicantList.isEmpty() ? null : applicantList.get(0);
    }

    public long getApplyCount(int eventId, boolean includeDuplicated) {
        return applicantDAO.getApplicantCount(eventId, includeDuplicated);
    }

    public List<Applicant> getApplicantList(int eventId) {
        return Lists.newArrayList(applicantDAO.getApplicant(eventId));
    }

    public List<Applicant> getApplicantList(int eventId, String whereKey, String whereValue) {
        return Lists.newArrayList(applicantDAO.getApplicant(eventId, whereKey, whereValue));
    }

    public List<DuplicatedApplicant> getDuplicatedApplicant(int eventId, String columnName) {
        return applicantDAO.getDuplicatedApplicant(eventId, columnName);
    }

    public void updateApplicantNotDuplicated(int eventId, String applyEmail) {
        applicantDAO.updateApplicantNotDuplicated(eventId, applyEmail);
    }

    public void updateApplicantDuplicated(int eventId, String applyEmail, String dupCheckAdminName) {
        applicantDAO.updateApplicantDuplicated(eventId, applyEmail, dupCheckAdminName);
    }
}
