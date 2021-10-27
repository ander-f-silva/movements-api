package br.com.bank.movements.business;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import br.com.bank.movements.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class RegisterEvent implements RegistrationEvent {

    private final EventRepository eventRepository;

    @Override
    public EventResult add(final Event event) {
        eventRepository.register(event);

        return new EventResult(new Account(event.getDestination(), event.getAmount()));
    }
}
