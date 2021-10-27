package br.com.bank.movements.repository;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class EventDaoTest {
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository = new EventDao(new HashMap<>());
    }

    @Test
    @DisplayName("should add a register of an event")
    void testAddAnEvent() {
        var fakeAccountId = 100;
        var fakeAmount = new BigDecimal(10);
        var fakeEvent = new Event(EventType.DEPOSIT, fakeAccountId, fakeAmount);

        var result = eventRepository.register(fakeEvent);

        assertThat(100, equalTo(result.getDestination()));
        assertThat(new BigDecimal(10), equalTo(result.getAmount()));
    }
}