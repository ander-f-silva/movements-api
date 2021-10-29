package br.com.bank.movements.business;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import br.com.bank.movements.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class DepositMovement implements MovementOperation {
    private final EventRepository eventRepository;

    @Override
    public Optional<EventResult> effect(final Event event) {
        eventRepository.register(event.getDestination(), event);

        var eventsOfTheAccount = eventRepository.listEventsByAccount(event.getDestination());

        return Optional.of(new EventResult(null, new Account(event.getDestination(), Balance.calculate(eventsOfTheAccount))));
    }
}
