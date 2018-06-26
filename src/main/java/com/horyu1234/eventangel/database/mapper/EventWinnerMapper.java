package com.horyu1234.eventangel.database.mapper;

import com.horyu1234.eventangel.domain.EventWinner;
import com.horyu1234.eventangel.factory.DateFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by horyu on 2018-06-17.
 */
@Component
public class EventWinnerMapper implements RowMapper<EventWinner> {
    @Override
    public EventWinner mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventWinner eventWinner = new EventWinner();
        eventWinner.setEventId(rs.getInt("EVENT_ID"));
        eventWinner.setPrizeId(rs.getInt("PRIZE_ID"));
        eventWinner.setApplyEmail(rs.getString("APPLY_EMAIL"));
        eventWinner.setWinTime(LocalDateTime.parse(rs.getString("WIN_TIME"), DateFactory.DATABASE_FORMAT));

        return eventWinner;
    }
}
