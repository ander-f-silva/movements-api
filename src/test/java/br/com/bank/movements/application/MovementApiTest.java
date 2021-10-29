package br.com.bank.movements.application;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import br.com.bank.movements.dto.EventType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovementApiTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private Integer port;

    private HttpHeaders headers = new HttpHeaders();

    private static Stream<Arguments> registerEvents() {
        return Stream.of(
                Arguments.of("Create new account", new Event(EventType.DEPOSIT, 100, null, BigDecimal.TEN), new EventResult(null, new Account(100, BigDecimal.TEN)), HttpStatus.CREATED),
                Arguments.of("Does a new deposit", new Event(EventType.DEPOSIT, 100, null, BigDecimal.TEN), new EventResult(null, new Account(100, new BigDecimal(20))), HttpStatus.CREATED),
                Arguments.of("Withdraw the an account", new Event(EventType.WITHDRAW, null, 100, new BigDecimal(5)), new EventResult(new Account(100, new BigDecimal(15)), null), HttpStatus.CREATED),
                Arguments.of("Account not found", new Event(EventType.WITHDRAW, null, 200, new BigDecimal(5)), null, HttpStatus.NOT_FOUND),
                Arguments.of("Transfer between two accounts", new Event(EventType.TRANSFER, 300, 100, new BigDecimal(15)), new EventResult(new Account(100, new BigDecimal(0)), new Account(300, new BigDecimal(15))), HttpStatus.CREATED),
                Arguments.of("Origin account not found", new Event(EventType.TRANSFER, 300, 200, new BigDecimal(15)), null, HttpStatus.NOT_FOUND)
        );
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("registerEvents")
    @DisplayName("should add an event")
    void testAddAnEvent(String title, Event newEvent, EventResult expectedEventResult, HttpStatus httpStatus) {
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Event> entity = new HttpEntity<>(newEvent, headers);
        ResponseEntity<EventResult> response = restTemplate.postForEntity("http://localhost:" + port + "/event", entity, EventResult.class);

        assertThat(response.getStatusCode(), Matchers.is(httpStatus));

        var eventResult = response.getBody();

        if (eventResult != null) {
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
        }
    }
}