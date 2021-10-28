package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class RegisterEvent implements RegistrationEvent {
    private final MovementOperationFactory factory;

    @Override
    public Optional<EventResult> add(final Event event) {
        return factory.get(event.getEventType()).effect(event);
    }
}
