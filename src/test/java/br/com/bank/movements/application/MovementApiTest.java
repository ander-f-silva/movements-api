package br.com.bank.movements.application;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import br.com.bank.movements.dto.EventType;
import br.com.bank.movements.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.util.AssertionErrors;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovementApiTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EventRepository eventRepository;

    @LocalServerPort
    private Integer port;

    private HttpHeaders headers = new HttpHeaders();

    private static Stream<Arguments> registerEvents() {
        return Stream.of(
                Arguments.of("Create new account", new Event(EventType.DEPOSIT, "100", null, BigDecimal.TEN), new EventResult(null, new Account("100", BigDecimal.TEN)), HttpStatus.CREATED),
                Arguments.of("Does a new deposit", new Event(EventType.DEPOSIT, "100", null, BigDecimal.TEN), new EventResult(null, new Account("100", new BigDecimal(20))), HttpStatus.CREATED),
                Arguments.of("Withdraw the an account", new Event(EventType.WITHDRAW, null, "100", new BigDecimal(5)), new EventResult(new Account("100", new BigDecimal(15)), null), HttpStatus.CREATED),
                Arguments.of("Account not found", new Event(EventType.WITHDRAW, null, "200", new BigDecimal(5)), null, HttpStatus.NOT_FOUND),
                Arguments.of("Transfer between two accounts", new Event(EventType.TRANSFER, "300", "100", new BigDecimal(15)), new EventResult(new Account("100", new BigDecimal(0)), new Account("300", new BigDecimal(15))), HttpStatus.CREATED),
                Arguments.of("Origin account not found", new Event(EventType.TRANSFER, "300", "200", new BigDecimal(15)), null, HttpStatus.NOT_FOUND)
        );
    }

    private static Stream<Arguments> getBalance() {
        return Stream.of(
                Arguments.of("Get balance with success", "150", BigDecimal.TEN, HttpStatus.OK),
                Arguments.of("Account not found", "200", BigDecimal.ZERO, HttpStatus.NOT_FOUND)
        );
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("registerEvents")
    @DisplayName("should add an event")
    void testAddAnEvent(String title, Event newEvent, EventResult expectedEventResult, HttpStatus httpStatus) {
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Event> entity = new HttpEntity<>(newEvent, headers);

        if (httpStatus.is2xxSuccessful()) {
            ResponseEntity<EventResult> response = restTemplate.postForEntity("http://localhost:" + port + "/event", entity, EventResult.class);

            var eventResult = response.getBody();

            if (expectedEventResult.getDestination() != null && expectedEventResult.getOrigin() != null) {
                assertThat(eventResult.getOrigin().getId(), equalTo(expectedEventResult.getOrigin().getId()));
                assertThat(eventResult.getOrigin().getBalance(), equalTo(expectedEventResult.getOrigin().getBalance()));

                assertThat(eventResult.getDestination().getId(), equalTo(expectedEventResult.getDestination().getId()));
                assertThat(eventResult.getDestination().getBalance(), equalTo(expectedEventResult.getDestination().getBalance()));
            }

            if (expectedEventResult.getDestination() != null) {
                assertThat(eventResult.getDestination().getId(), equalTo(expectedEventResult.getDestination().getId()));
                assertThat(eventResult.getDestination().getBalance(), equalTo(expectedEventResult.getDestination().getBalance()));
            }

            if (expectedEventResult.getOrigin() != null) {
                assertThat(eventResult.getOrigin().getId(), equalTo(expectedEventResult.getOrigin().getId()));
                assertThat(eventResult.getOrigin().getBalance(), equalTo(expectedEventResult.getOrigin().getBalance()));
            }

            return;
        }

        if (httpStatus.is4xxClientError()) {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/event", entity, String.class);

            var notFoundReturn = response.getBody();

            assertThat(notFoundReturn, equalTo("0"));

            return;
        }

        AssertionErrors.fail("Fail in execute tests");
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("getBalance")
    @DisplayName("should get balance of a account")
    void testGetBalance(String title, String fakeAccountId, BigDecimal expectedBalance, HttpStatus httpStatus) {
        eventRepository.register("150", new Event(EventType.DEPOSIT, "150", null, BigDecimal.TEN));

        ResponseEntity<BigDecimal> response = restTemplate.getForEntity("http://localhost:" + port + "/balance?account_id=" + fakeAccountId, BigDecimal.class);

        assertThat(response.getStatusCode(), equalTo(httpStatus));
        assertThat(response.getBody(), equalTo(expectedBalance));
    }

    @Test
    @DisplayName("should reset all data")
    void testReset() {
        eventRepository.register("150", new Event(EventType.DEPOSIT, "150", null, BigDecimal.TEN));

        assertThat(eventRepository.listEventsByAccount("150").size(), equalTo(1));

        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Event> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/reset", entity, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo("OK"));

        assertThat(eventRepository.listEventsByAccount("150"), nullValue());
    }
}