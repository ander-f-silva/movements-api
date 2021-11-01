package br.com.bank.movements.application;

import br.com.bank.movements.business.GetBalance;
import br.com.bank.movements.business.RegistrationEvent;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MovementApi {
    public static Logger logger = LoggerFactory.getLogger(MovementApi.class);

    public static final String NOT_FOUND_RETURN = "0";

    private final RegistrationEvent registrationEvent;
    private final GetBalance getBalance;

    @PostMapping(value = "/event")
    public ResponseEntity<?> registerEvent(@RequestBody Event newEvent) {
        Optional<EventResult> eventResult = registrationEvent.add(newEvent);

        if (eventResult.isPresent()) {
            var valueEventsResult = eventResult.get();

            logger.info("[action: 'ADD_NEW_EVENT'][request: {}][response: {}] Create the event with success", newEvent, valueEventsResult);
            return ResponseEntity.status(HttpStatus.CREATED).body(valueEventsResult);
        } else {
            logger.info("[action: 'ADD_NEW_EVENT'][request: {}] Account not found", newEvent);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_RETURN);
        }
    }

    @GetMapping(value = "/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestParam("account_id") String accountId) {
        Optional<BigDecimal> balance = getBalance.findByAccount(accountId);

        if (balance.isPresent()) {
            var valueEventsResult = balance.get();

            logger.info("[action: 'GET_BALANCE'][query_string:(account_id: {})][response: {}] Create the event with success", accountId, valueEventsResult);
            return ResponseEntity.status(HttpStatus.OK).body(valueEventsResult);
        } else {
            logger.info("[action: 'GET_BALANCE'][query_string:(account_id: {})] Account not found", accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BigDecimal.ZERO);
        }
    }
}
