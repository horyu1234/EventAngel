package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.database.mapper.EventWinnerMapper;
import com.horyu1234.eventangel.domain.EventWinner;
import com.horyu1234.eventangel.factory.DateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by horyu on 2018-04-15
 */
@Repository
public class EventWinnerDAO {
    private JdbcTemplate jdbcTemplate;
    private EventWinnerMapper eventWinnerMapper;

    @Autowired
    public EventWinnerDAO(JdbcTemplate jdbcTemplate, EventWinnerMapper eventWinnerMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventWinnerMapper = eventWinnerMapper;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `EVENT_WINNER` ( " +
                "`EVENT_ID` INT(11) NOT NULL, " +
                "`PRIZE_ID` INT(11) NOT NULL, " +
                "`APPLY_EMAIL` VARCHAR(255) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`WIN_TIME` DATETIME NOT NULL, " +
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
                eventWinnerMapper);
    }

    public void insertEventWinner(int eventId, int prizeId, String applyEmail, LocalDateTime winTime) {
        jdbcTemplate.update("INSERT INTO `EVENT_WINNER` (EVENT_ID, PRIZE_ID, APPLY_EMAIL, WIN_TIME) VALUES (?, ?, ?, ?);",
                eventId, prizeId, applyEmail, DateFactory.DATABASE_FORMAT.format(winTime));
    }

    public void resetEventWinner(int eventId) {
        jdbcTemplate.update("DELETE FROM `EVENT_WINNER` " +
                        "WHERE EVENT_ID = ?;",
                eventId);
    }
}
