package br.com.bank.movements.business;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import br.com.bank.movements.dto.EventType;
import br.com.bank.movements.repository.EventRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationEventTest {
    private static RegistrationEvent registrationEvent;

    private static Stream<Arguments> registerEvents() {
        return Stream.of(
                Arguments.of(new Event(EventType.DEPOSIT, 100, null, BigDecimal.TEN), new EventResult(new Account(100, BigDecimal.TEN))),
                Arguments.of(new Event(EventType.DEPOSIT, 100, null, BigDecimal.TEN), new EventResult(new Account(100, new BigDecimal(20)))),
                Arguments.of(new Event(EventType.WITHDRAW, null, 100, new BigDecimal(5)), new EventResult(new Account(100, new BigDecimal(15))))
        );
    }

    @BeforeAll
    static void setUp() {
        var mockEventRepository = new MockEventRepository();
        var movementOperation = new MovementOperationFactory(new DepositMovement(mockEventRepository), new WithdrawMovement(mockEventRepository), new HashMap<>());

        movementOperation.init();

        registrationEvent = new RegisterEvent(movementOperation);
    }

    @ParameterizedTest
    @MethodSource("registerEvents")
    @DisplayName("should add an event")
    void testAddAnEvent(Event fakeEvent, EventResult expectedEventResult) {
        var result = registrationEvent.add(fakeEvent);

        assertThat(expectedEventResult.getOrigin().getId(), equalTo(result.getOrigin().getId()));
        assertThat(expectedEventResult.getOrigin().getBalance(), equalTo(result.getOrigin().getBalance()));
    }

    static class MockEventRepository implements EventRepository {
        private Map<Integer, List<Event>> mockData = new HashMap<>();

        @Override
        public Event register(Integer accountId, Event event) {
            if (mockData.containsKey(accountId)) {
                mockData.get(accountId).add(event);
            } else {
                var list = new ArrayList<Event>();
                list.add(event);

                mockData.put(accountId, list);
            }

            return event;
        }

        @Override
        public List<Event> listEventsByAccount(Integer id) {
            return mockData.get(id);
        }
    }
}
