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
class TransferMovement implements MovementOperation {
    private final EventRepository eventRepository;

    @Override
    public Optional<EventResult> effect(final Event event) {
        var transferEventToAccountOrigin = Event.builder()
                .origin(event.getOrigin())
                .amount(event.getAmount().negate())
                .build();

        eventRepository.register(event.getOrigin(), transferEventToAccountOrigin);
        eventRepository.register(event.getDestination(), event);

        var eventsOfTheAccountOrigin = eventRepository.listEventsByAccount(transferEventToAccountOrigin.getOrigin());
        var eventsOfTheAccountDestination = eventRepository.listEventsByAccount(event.getDestination());

        return Optional.of(new EventResult(new Account(event.getOrigin(), Balance.calculate(eventsOfTheAccountOrigin)), new Account(event.getDestination(), Balance.calculate(eventsOfTheAccountDestination))));
    }
}
