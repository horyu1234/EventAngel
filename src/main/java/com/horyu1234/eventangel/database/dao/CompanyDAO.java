package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.database.mapper.CompanyMapper;
import com.horyu1234.eventangel.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDAO {
    private JdbcTemplate jdbcTemplate;
    private CompanyMapper companyMapper;

    @Autowired
    public CompanyDAO(JdbcTemplate jdbcTemplate, CompanyMapper companyMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.companyMapper = companyMapper;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `COMPANY` ( " +
                "`EVENT_ID` INT(11) NOT NULL, " +
                "`COMPANY_ID` INT(11) NOT NULL, " +
                "`COMPANY_NAME` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`COMPANY_DETAIL` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`COMP_LOGO_IMG_FNAME` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "PRIMARY KEY (`EVENT_ID`, `COMPANY_ID`) " +
                ") " +
                "COLLATE='utf8mb4_unicode_ci' " +
                "ENGINE=InnoDB;");
    }

    public Company getCompany(int eventId, int companyId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `COMPANY` " +
                        "WHERE EVENT_ID = ? AND COMPANY_ID = ? " +
                        "ORDER BY `COMPANY_ID` " +
                        "DESC;",
                new Object[]{eventId, companyId},
                companyMapper);
    }

    public List<Company> getCompanyList(int eventId) {
        return jdbcTemplate.query(
                "SELECT * FROM `COMPANY` " +
                        "WHERE EVENT_ID = ? " +
                        "ORDER BY `COMPANY_ID` " +
                        "ASC;",
                new Object[]{eventId},
                companyMapper);
    }

    public int getMaxCompanyId(int eventId) {
        return jdbcTemplate.queryForObject(
                "SELECT MAX(COMPANY_ID) " +
                        "FROM `COMPANY` " +
                        "WHERE EVENT_ID = ?;",
                new Object[]{eventId},
                Integer.class);
    }

    public void updateOrInsertCompany(Company company) {
        jdbcTemplate.update("INSERT INTO `COMPANY` (EVENT_ID, COMPANY_ID, COMPANY_NAME, COMPANY_DETAIL) VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE COMPANY_ID = ?, COMPANY_NAME = ?, COMPANY_DETAIL = ?;",
                company.getEventId(), company.getCompanyId(), company.getCompanyName(), company.getCompanyDetail(),
                company.getCompanyId(), company.getCompanyName(), company.getCompanyDetail());
    }

    public void updateCompanyLogo(int eventId, int companyId, String fileName) {
        jdbcTemplate.update("UPDATE `COMPANY` " +
                        "SET COMP_LOGO_IMG_FNAME = ? " +
                        "WHERE EVENT_ID = ? AND COMPANY_ID = ?",
                fileName, eventId, companyId);
    }

    public void deleteCompany(int eventId, int companyId) {
        jdbcTemplate.update("DELETE FROM `COMPANY` " +
                        "WHERE EVENT_ID = ? AND COMPANY_ID = ?",
                eventId, companyId);
    }
}
