package br.com.bank.movements.business;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
class RegisterEvent implements RegistrationEvent {

    private final Map<Integer, Event> eventRepository;

    @Override
    public EventResult add(final Event event) {
        eventRepository.put(event.getDestination(), event);

        return new EventResult(new Account(event.getDestination(), event.getAmount()));
    }
}
