package br.com.bank.movements.application;

import br.com.bank.movements.business.RegistrationEvent;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/movement-api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovementApi {
    private final RegistrationEvent registrationEvent;

    @PostMapping(value = "/event")
    public ResponseEntity<EventResult> registerEvent(@RequestBody Event newEvent) {
        Optional<EventResult> eventResult = registrationEvent.add(newEvent);

        if (eventResult.isPresent()) {
            var valueEventsResult = eventResult.get();

            return ResponseEntity.status(HttpStatus.CREATED).body(valueEventsResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
