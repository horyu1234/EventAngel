package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by horyu on 2018-04-15
 */
@Repository
public class CompanyDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `COMPANY` ( " +
                "`EVENT_ID` INT(11) NOT NULL, " +
                "`COMPANY_ID` INT(11) NOT NULL, " +
                "`COMPANY_NAME` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`COMPANY_DETAIL` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
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
                new BeanPropertyRowMapper<>(Company.class));
    }

    public List<Company> getCompanyList(int eventId) {
        return jdbcTemplate.query(
                "SELECT * FROM `COMPANY` " +
                        "WHERE EVENT_ID = ? " +
                        "ORDER BY `COMPANY_ID` " +
                        "ASC;",
                new Object[]{eventId},
                new BeanPropertyRowMapper<>(Company.class));
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

    public void deleteCompany(int eventId, int companyId) {
        jdbcTemplate.update("DELETE FROM `COMPANY` " +
                        "WHERE EVENT_ID = ? AND COMPANY_ID = ?",
                eventId, companyId);
    }
}
