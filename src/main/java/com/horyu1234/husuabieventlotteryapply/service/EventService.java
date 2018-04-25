package com.horyu1234.husuabieventlotteryapply.service;

import com.horyu1234.husuabieventlotteryapply.constant.EventDetailStatus;
import com.horyu1234.husuabieventlotteryapply.constant.EventStatus;
import com.horyu1234.husuabieventlotteryapply.database.dao.EventDAO;
import com.horyu1234.husuabieventlotteryapply.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if (event.getEventStatus() != EventStatus.OPEN) {
            return EventDetailStatus.CLOSE;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse(event.getEventStartTime(), dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(event.getEventEndTime(), dateTimeFormatter);

        if (currentTime.isBefore(startTime)) {
            return EventDetailStatus.START_SOON;
        } else if (currentTime.isAfter(endTime)) {
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
        event.setEventStartTime("2018-01-01 01:00");
        event.setEventEndTime("2018-01-01 01:00");

        updateEvent(event);
    }
}
