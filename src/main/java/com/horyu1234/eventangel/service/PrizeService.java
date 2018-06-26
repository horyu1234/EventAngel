package com.horyu1234.eventangel.service;

import com.horyu1234.eventangel.database.dao.CompanyDAO;
import com.horyu1234.eventangel.database.dao.PrizeDAO;
import com.horyu1234.eventangel.domain.Company;
import com.horyu1234.eventangel.domain.CompanyAndPrize;
import com.horyu1234.eventangel.domain.Prize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by horyu on 2018-04-06
 */
@Service
public class PrizeService {
    private CompanyDAO companyDAO;
    private PrizeDAO prizeDAO;

    @PostConstruct
    public void init() {
        prizeDAO.createTableIfNotExist();
    }

    @Autowired
    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Autowired
    public void setPrizeDAO(PrizeDAO prizeDAO) {
        this.prizeDAO = prizeDAO;
    }

    @CacheEvict(value = {"prizeService.companyGiftData", "prizeService.totalPrizeAmount"}, allEntries = true)
    public void savePrize(Prize prize) {
        int eventId = prize.getEventId();

        if (prize.getPrizeId() == 0) {
            int maxPrizeId = 0;
            if (!prizeDAO.getPrizeList(eventId).isEmpty()) {
                maxPrizeId = prizeDAO.getMaxPrizeId(eventId);
            }

            prize.setPrizeId(maxPrizeId + 1);
        }

        prizeDAO.updateOrInsertPrize(prize);
    }

    @CacheEvict(value = {"prizeService.companyGiftData", "prizeService.totalPrizeAmount"}, allEntries = true)
    public void deletePrize(int eventId, int prizeId) {
        prizeDAO.deletePrize(eventId, prizeId);
    }

    @CacheEvict(value = {"prizeService.companyGiftData", "prizeService.totalPrizeAmount"}, allEntries = true)
    public void deletePrizeByCompanyId(int eventId, int companyId) {
        prizeDAO.deletePrizeByCompanyId(eventId, companyId);
    }

    @Cacheable(value = "prizeService.companyGiftData")
    public List<CompanyAndPrize> getCompanyGiftData(int eventId) {
        List<Prize> prizeList = prizeDAO.getPrizeList(eventId);

        List<CompanyAndPrize> companyAndPrizeList = new ArrayList<>();
        for (Prize prize : prizeList) {
            Company company = companyDAO.getCompany(eventId, prize.getCompanyId());

            CompanyAndPrize companyAndPrize = new CompanyAndPrize(company, prize);
            companyAndPrizeList.add(companyAndPrize);
        }

        return companyAndPrizeList;
    }

    @Cacheable(value = "prizeService.totalPrizeAmount")
    public int getTotalPrizeAmount(int eventId) {
        int totalAmount = 0;
        for (CompanyAndPrize companyAndPrize : getCompanyGiftData(eventId)) {
            totalAmount += companyAndPrize.getPrizeAmount();
        }

        return totalAmount;
    }
}
