package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.domain.EventWinner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by horyu on 2018-04-15
 */
@Repository
public class EventWinnerDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `EVENT_WINNER` ( " +
                "`EVENT_ID` INT(11) NOT NULL, " +
                "`PRIZE_ID` INT(11) NOT NULL, " +
                "`APPLY_EMAIL` VARCHAR(255) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "PRIMARY KEY (`EVENT_ID`, `PRIZE_ID`, `APPLY_EMAIL`) " +
                ") " +
                "COLLATE='utf8mb4_unicode_ci' " +
                "ENGINE=InnoDB;");
    }

    public List<EventWinner> getWinnerList(int eventId) {
        return jdbcTemplate.query(
                "SELECT * FROM `EVENT_WINNER` " +
                        "WHERE EVENT_ID = ?;",
                new Object[]{eventId},
                new BeanPropertyRowMapper<>(EventWinner.class));
    }

    public void insertEventWinner(int eventId, int prizeId, String applyEmail) {
        jdbcTemplate.update("INSERT INTO `EVENT_WINNER` (EVENT_ID, PRIZE_ID, APPLY_EMAIL) VALUES (?, ?, ?);",
                eventId, prizeId, applyEmail);
    }

    public void resetEventWinner(int eventId) {
        jdbcTemplate.update("DELETE FROM `EVENT_WINNER` " +
                        "WHERE EVENT_ID = ?;",
                eventId);
    }
}
