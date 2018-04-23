package com.horyu1234.husuabieventlotteryapply.database.dao;

import com.horyu1234.husuabieventlotteryapply.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by horyu on 2018-04-15
 */
@Repository
public class EventDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `EVENT` ( " +
                "`EVENT_ID` INT(11) NOT NULL AUTO_INCREMENT, " +
                "`EVENT_TITLE` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`EVENT_DETAIL` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`EVENT_STATUS` VARCHAR(10) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`EVENT_START_TIME` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`EVENT_END_TIME` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "PRIMARY KEY (`EVENT_ID`) " +
                ") " +
                "COLLATE='utf8mb4_unicode_ci' " +
                "ENGINE=InnoDB;");
    }

    public Event getCurrentEvent() {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM `EVENT` " +
                        "ORDER BY EVENT_ID " +
                        "DESC LIMIT 1;",
                new BeanPropertyRowMapper<>(Event.class));
    }

    public List<Event> getEventList() {
        return jdbcTemplate.query(
                "SELECT * FROM `EVENT` " +
                        "ORDER BY EVENT_ID " +
                        "DESC;",
                new BeanPropertyRowMapper<>(Event.class));
    }

    public void updateOrInsertEvent(Event event) {
        jdbcTemplate.update("INSERT INTO `EVENT` (EVENT_ID, EVENT_TITLE, EVENT_DETAIL, EVENT_STATUS, EVENT_START_TIME, EVENT_END_TIME) VALUES (?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE EVENT_TITLE = ?, EVENT_DETAIL = ?, EVENT_STATUS = ?, EVENT_START_TIME = ?, EVENT_END_TIME = ?;",
                event.getEventId(), event.getEventTitle(), event.getEventDetail(), event.getEventStatus().name(), event.getEventStartTime(), event.getEventEndTime(),
                event.getEventTitle(), event.getEventDetail(), event.getEventStatus().name(), event.getEventStartTime(), event.getEventEndTime());
    }
}
