package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;

import java.util.Optional;

public interface RegistrationEvent {
    Optional<EventResult> add(final Event fakeEvent);
}
