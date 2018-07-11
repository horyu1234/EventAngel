package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.database.mapper.EventMapper;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.factory.DateFactory;
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
    private EventMapper eventMapper;

    @Autowired
    public EventDAO(JdbcTemplate jdbcTemplate, EventMapper eventMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventMapper = eventMapper;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `EVENT` ( " +
                "`EVENT_ID` INT(11) NOT NULL AUTO_INCREMENT, " +
                "`EVENT_TITLE` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`EVENT_DETAIL` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`EVENT_STATUS` VARCHAR(10) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`EVENT_START_TIME` DATETIME NOT NULL, " +
                "`EVENT_END_TIME` DATETIME NOT NULL, " +
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
                eventMapper);
    }

    public List<Event> getEventList() {
        return jdbcTemplate.query(
                "SELECT * FROM `EVENT` " +
                        "ORDER BY EVENT_ID " +
                        "DESC;",
                new BeanPropertyRowMapper<>(Event.class));
    }

    public void updateOrInsertEvent(Event event) {
        String eventStartTime = DateFactory.DATABASE_FORMAT.format(event.getEventStartTime());
        String eventEndTime = DateFactory.DATABASE_FORMAT.format(event.getEventEndTime());

        jdbcTemplate.update("INSERT INTO `EVENT` (EVENT_ID, EVENT_TITLE, EVENT_DETAIL, EVENT_STATUS, EVENT_START_TIME, EVENT_END_TIME) VALUES (?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE EVENT_TITLE = ?, EVENT_DETAIL = ?, EVENT_STATUS = ?, EVENT_START_TIME = ?, EVENT_END_TIME = ?;",
                event.getEventId(), event.getEventTitle(), event.getEventDetail(), event.getEventStatus().name(), eventStartTime, eventEndTime,
                event.getEventTitle(), event.getEventDetail(), event.getEventStatus().name(), eventStartTime, eventEndTime);
    }
}
