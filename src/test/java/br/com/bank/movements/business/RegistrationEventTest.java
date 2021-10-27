package br.com.bank.movements.business;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import br.com.bank.movements.dto.EventType;
import br.com.bank.movements.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationEventTest {
    private RegistrationEvent registrationEvent = new RegisterEvent(new MockEventRepository());

    private static Stream<Arguments> registerEvents() {
        return Stream.of(
                Arguments.of(new Event(EventType.DEPOSIT, 100, BigDecimal.TEN), new EventResult(new Account(100, BigDecimal.TEN))),
                Arguments.of(new Event(EventType.DEPOSIT, 100, BigDecimal.TEN), new EventResult(new Account(100, new BigDecimal(20))))
        );
    }

    @ParameterizedTest
    @MethodSource("registerEvents")
    @DisplayName("should add an event")
    void testAddAnEvent(Event fakeEvent, EventResult expectedEventResult) {
        var result = registrationEvent.add(fakeEvent);

        assertThat(expectedEventResult.getOrigin().getId(), equalTo(result.getOrigin().getId()));
        assertThat(expectedEventResult.getOrigin().getBalance(), equalTo(result.getOrigin().getBalance()));
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
