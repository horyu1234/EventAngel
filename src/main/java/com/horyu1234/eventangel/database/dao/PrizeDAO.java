package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.domain.Prize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by horyu on 2018-04-15
 */
@Repository
public class PrizeDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `PRIZE` ( " +
                "`EVENT_ID` INT(11) NOT NULL, " +
                "`PRIZE_ID` INT(11) NOT NULL, " +
                "`COMPANY_ID` INT(11) NOT NULL, " +
                "`PRIZE_NAME` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`PRIZE_AMOUNT` INT(11) NOT NULL, " +
                "PRIMARY KEY (`EVENT_ID`, `PRIZE_ID`) " +
                ") " +
                "COLLATE='utf8mb4_unicode_ci' " +
                "ENGINE=InnoDB;");
    }

    public List<Prize> getPrizeList(int eventId) {
        return jdbcTemplate.query(
                "SELECT * FROM `PRIZE` " +
                        "WHERE EVENT_ID = ? " +
                        "ORDER BY `PRIZE_ID` " +
                        "DESC;",
                new Object[]{eventId},
                new BeanPropertyRowMapper<>(Prize.class));
    }

    public int getMaxPrizeId(int eventId) {
        return jdbcTemplate.queryForObject(
                "SELECT MAX(PRIZE_ID) " +
                        "FROM `PRIZE` " +
                        "WHERE EVENT_ID = ?;",
                new Object[]{eventId},
                Integer.class);
    }

    public void updateOrInsertPrize(Prize prize) {
        jdbcTemplate.update("INSERT INTO `PRIZE` (EVENT_ID, PRIZE_ID, COMPANY_ID, PRIZE_NAME, PRIZE_AMOUNT) VALUES (?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE PRIZE_ID = ?, COMPANY_ID = ?, PRIZE_NAME = ?, PRIZE_AMOUNT = ?;",
                prize.getEventId(), prize.getPrizeId(), prize.getCompanyId(), prize.getPrizeName(), prize.getPrizeAmount(),
                prize.getPrizeId(), prize.getCompanyId(), prize.getPrizeName(), prize.getPrizeAmount());
    }

    public void deletePrize(int eventId, int prizeId) {
        jdbcTemplate.update("DELETE FROM `PRIZE` " +
                        "WHERE EVENT_ID = ? AND PRIZE_ID = ?",
                eventId, prizeId);
    }

    public void deletePrizeByCompanyId(int eventId, int companyId) {
        jdbcTemplate.update("DELETE FROM `PRIZE` " +
                        "WHERE EVENT_ID = ? AND COMPANY_ID = ?",
                eventId, companyId);
    }
}
