package com.horyu1234.husuabieventlotteryapply.database.mapper;

import com.horyu1234.husuabieventlotteryapply.constant.EventStatus;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import com.horyu1234.husuabieventlotteryapply.factory.DateFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by horyu on 2018-06-17.
 */
public class EventMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.setEventId(rs.getInt("EVENT_ID"));
        event.setEventTitle(rs.getString("EVENT_TITLE"));
        event.setEventDetail(rs.getString("EVENT_DETAIL"));
        event.setEventStatus(EventStatus.valueOf(rs.getString("EVENT_STATUS")));

        event.setEventStartTime(LocalDateTime.parse(rs.getString("EVENT_START_TIME"), DateFactory.DATABASE_FORMAT));
        event.setEventEndTime(LocalDateTime.parse(rs.getString("EVENT_END_TIME"), DateFactory.DATABASE_FORMAT));

        return event;
    }
}
