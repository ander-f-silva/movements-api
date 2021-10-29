package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;

import java.util.Optional;

interface MovementOperation {
    Optional<EventResult> effect(final Event event);
}
