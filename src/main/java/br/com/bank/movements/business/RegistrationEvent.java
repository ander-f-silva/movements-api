package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;

public interface RegistrationEvent {
    EventResult add(final Event fakeEvent);
}
