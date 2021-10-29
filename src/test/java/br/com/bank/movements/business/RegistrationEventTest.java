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
import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegistrationEventTest {
    private static RegistrationEvent registrationEvent;

    private static Stream<Arguments> registerEvents() {
        return Stream.of(
                Arguments.of("Create new account", new Event(EventType.DEPOSIT, 100, null, BigDecimal.TEN), Optional.of(new EventResult(null, new Account(100, BigDecimal.TEN)))),
                Arguments.of("Does a new deposit", new Event(EventType.DEPOSIT, 100, null, BigDecimal.TEN), Optional.of(new EventResult(null, new Account(100, new BigDecimal(20))))),
                Arguments.of("Withdraw the an account", new Event(EventType.WITHDRAW, null, 100, new BigDecimal(5)), Optional.of(new EventResult(new Account(100, new BigDecimal(15)), null))),
                Arguments.of("Account not found", new Event(EventType.WITHDRAW, null, 200, new BigDecimal(5)), Optional.empty()),
                Arguments.of("Transfer between two accounts", new Event(EventType.TRANSFER, 300, 100, new BigDecimal(15)), Optional.of(new EventResult(new Account(100, new BigDecimal(0)), new Account(300, new BigDecimal(15))))),
                Arguments.of("Origin account not found", new Event(EventType.TRANSFER, 300, 200, new BigDecimal(15)), Optional.empty())
        );
    }

    @BeforeAll
    static void setUp() {
        var mockEventRepository = new MockEventRepository();
        var movementOperation = new MovementOperationFactory(new DepositMovement(mockEventRepository), new WithdrawMovement(mockEventRepository), new TransferMovement(mockEventRepository), new HashMap<>());

        movementOperation.init();

        registrationEvent = new RegisterEvent(movementOperation);
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("registerEvents")
    @DisplayName("should add an event")
    void testAddAnEvent(String title, Event fakeEvent, Optional<EventResult> expectedEventResult) {
        var result = registrationEvent.add(fakeEvent);

        if (result.isPresent()) {
            var expected = expectedEventResult.get();

            if (expected.getDestination() != null && expected.getOrigin() != null) {
                assertThat(result.get().getOrigin().getId(), equalTo(expected.getOrigin().getId()));
                assertThat(result.get().getOrigin().getBalance(), equalTo(expected.getOrigin().getBalance()));

                assertThat(result.get().getDestination().getId(), equalTo(expected.getDestination().getId()));
                assertThat(result.get().getDestination().getBalance(), equalTo(expected.getDestination().getBalance()));
            }

            if (expected.getDestination() != null) {
                assertThat(result.get().getDestination().getId(), equalTo(expected.getDestination().getId()));
                assertThat(result.get().getDestination().getBalance(), equalTo(expected.getDestination().getBalance()));
            }

            if (expected.getOrigin() != null) {
                assertThat(result.get().getOrigin().getId(), equalTo(expected.getOrigin().getId()));
                assertThat(result.get().getOrigin().getBalance(), equalTo(expected.getOrigin().getBalance()));
            }
        } else {
            assertThat(expectedEventResult.isEmpty(), is(true));
        }
    }

    static class MockEventRepository implements EventRepository {
        private Map<Integer, List<Event>> mockData = new HashMap<>();

        public MockEventRepository() {
            var list = new ArrayList<Event>();
            list.add( new Event(EventType.DEPOSIT, 0, null, BigDecimal.ZERO));

            mockData.put(300, list);
        }

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

        @Override
        public boolean exists(Integer accountId) {
            return mockData.containsKey(accountId);
        }
    }
}
