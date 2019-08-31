package com.horyu1234.eventangel.service;

import com.horyu1234.eventangel.constant.EventDetailStatus;
import com.horyu1234.eventangel.constant.EventStatus;
import com.horyu1234.eventangel.database.dao.EventDAO;
import com.horyu1234.eventangel.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by horyu on 2018-04-06
 */
@Service
public class EventService {
    private EventDAO eventDAO;

    @PostConstruct
    public void init() {
        eventDAO.createTableIfNotExist();
    }

    @Autowired
    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @CacheEvict(value = {"event.current_event", "event.list"}, allEntries = true)
    public void updateEvent(Event event) {
        eventDAO.updateOrInsertEvent(event);
    }

    @Cacheable(value = "event.current_event")
    public Event getCurrentEvent() {
        return eventDAO.getCurrentEvent();
    }

    @Cacheable(value = "event.list")
    public List<Event> getEventList() {
        return eventDAO.getEventList();
    }

    public EventDetailStatus getEventDetailStatus(Event event) {
        if (event.getEventStatus() == EventStatus.CLOSE) {
            return EventDetailStatus.CLOSE;
        } else if (event.getEventStatus() == EventStatus.LOTTERY) {
            return EventDetailStatus.LOTTERY;
        }

        LocalDateTime currentTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        if (currentTime.isBefore(event.getEventStartTime())) {
            return EventDetailStatus.START_SOON;
        } else if (currentTime.isAfter(event.getEventEndTime())) {
            return EventDetailStatus.ALREADY_END;
        } else {
            return EventDetailStatus.OPEN;
        }
    }

    public void newEvent() {
        Event event = new Event();
        event.setEventTitle("허수아비 X차 이벤트");
        event.setEventDetail("이벤트 세부 일정");
        event.setEventStatus(EventStatus.CLOSE);
        event.setEventStartTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        event.setEventEndTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());

        updateEvent(event);
    }
}
