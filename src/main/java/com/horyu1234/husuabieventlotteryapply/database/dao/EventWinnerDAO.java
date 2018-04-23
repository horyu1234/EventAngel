package com.horyu1234.husuabieventlotteryapply.database.dao;

import com.horyu1234.husuabieventlotteryapply.domain.Applicant;
import com.horyu1234.husuabieventlotteryapply.domain.EventWinner;
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
                "`APPLY_EMAIL` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`APPLY_YOUTUBE_NICKNAME` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`APPLY_TIME` DATETIME NOT NULL, " +
                "`APPLY_IP_ADDRESS` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`APPLY_USER_AGENT` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`APPLY_FINGERPRINT2` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci' " +
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

    public void insertEventWinner(int eventId, int prizeId, Applicant applicant) {
        jdbcTemplate.update("INSERT INTO `EVENT_WINNER` (EVENT_ID, PRIZE_ID, APPLY_EMAIL, APPLY_YOUTUBE_NICKNAME, APPLY_TIME, APPLY_IP_ADDRESS, APPLY_USER_AGENT, APPLY_FINGERPRINT2) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
                eventId, prizeId, applicant.getEmail(), applicant.getYoutubeNickname(), applicant.getApplyTime(), applicant.getIpAddress(), applicant.getUserAgent(), applicant.getFingerprint2());
    }

    public void resetEventWinner(int eventId) {
        jdbcTemplate.update("DELETE FROM `EVENT_WINNER` " +
                        "WHERE EVENT_ID = ?;",
                eventId);
    }
}
