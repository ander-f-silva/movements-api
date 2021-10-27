package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventType;
import br.com.bank.movements.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationEventTest {
    private RegistrationEvent registrationEvent;

    @BeforeEach
    void setUp() {
        registrationEvent = new RegisterEvent(new MockEventRepository());
    }

    @Test
    @DisplayName("should add an event")
    void testAddAnEvent() {
        var fakeAccountId = 100;
        var fakeAmount = new BigDecimal(10);
        var fakeEvent = new Event(EventType.DEPOSIT, fakeAccountId, fakeAmount);

        var result = registrationEvent.add(fakeEvent);

        assertThat(100, equalTo(result.getOrigin().getId()));
        assertThat(new BigDecimal(10), equalTo(result.getOrigin().getBalance()));
    }

    class MockEventRepository implements EventRepository {
        private List<Event> mockData = new ArrayList<>();

        @Override
        public Event register(Event event) {
            mockData.add(event);
            return event;
        }
    }
}
