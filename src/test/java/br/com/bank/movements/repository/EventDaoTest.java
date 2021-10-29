package br.com.bank.movements.repository;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class EventDaoTest {

    @Test
    @DisplayName("should add a register of an event")
    void testAddAnEvent() {
        var eventRepository = new EventDao(new HashMap<>());

        var fakeAccountId = 100;
        var fakeAmount = new BigDecimal(10);
        var fakeEvent = new Event(EventType.DEPOSIT, fakeAccountId, null, fakeAmount);

        var result = eventRepository.register(fakeAccountId, fakeEvent);

        assertThat(100, equalTo(result.getDestination()));
        assertThat(new BigDecimal(10), equalTo(result.getAmount()));
    }

    @Test
    @DisplayName("should listed the events of an account")
    void testGetAnEvent() {
        var fakeAccountId = 100;
        var mockData = new HashMap<Integer, List<Event>>();
        var mockEvent = new Event(EventType.DEPOSIT, fakeAccountId, null, BigDecimal.TEN);

        mockData.put(100, Arrays.asList(mockEvent, mockEvent));

        var eventRepository = new EventDao(mockData);

        var result = eventRepository.listEventsByAccount(fakeAccountId);

        assertThat(2, equalTo(result.size()));

        assertThat(EventType.DEPOSIT, equalTo(result.get(0).getEventType()));
        assertThat(100, equalTo(result.get(0).getDestination()));
        assertThat(new BigDecimal(10), equalTo(result.get(0).getAmount()));

        assertThat(EventType.DEPOSIT, equalTo(result.get(1).getEventType()));
        assertThat(100, equalTo(result.get(1).getDestination()));
        assertThat(new BigDecimal(10), equalTo(result.get(1).getAmount()));
    }

    @Test
    @DisplayName("should verify if event exist")
    void testExistsAnEvent() {
        var fakeAccountId = 100;
        var mockData = new HashMap<Integer, List<Event>>();
        var mockEvent = new Event(EventType.DEPOSIT, fakeAccountId, null, BigDecimal.TEN);

        mockData.put(100, Arrays.asList(mockEvent, mockEvent));

        var eventRepository = new EventDao(mockData);

        var result = eventRepository.exists(fakeAccountId);

        assertThat(true, is(result));
    }

    @Test
    @DisplayName("should verify if not event exist")
    void testNotExistsAnEvent() {
        var eventRepository = new EventDao( new HashMap<>());

        var result = eventRepository.exists(200);

        assertThat(false, is(result));
    }
}