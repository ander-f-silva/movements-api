package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;

interface MovementOperation {
    EventResult effect(final Event event);
}
