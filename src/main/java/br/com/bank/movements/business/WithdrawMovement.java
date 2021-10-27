package br.com.bank.movements.business;

import br.com.bank.movements.dto.Account;
import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import br.com.bank.movements.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class WithdrawMovement implements MovementOperation {
    private final EventRepository eventRepository;

    @Override
    public EventResult effect(final Event event) {
        var withdrawEvent = Event.builder()
                .origin(event.getOrigin())
                .amount(event.getAmount().negate())
                .build();

        eventRepository.register(withdrawEvent.getOrigin(), withdrawEvent);

        var eventsOfTheAccount = eventRepository.listEventsByAccount(event.getOrigin());

        return new EventResult(new Account(event.getOrigin(), Balance.calculate(eventsOfTheAccount)));
    }
}