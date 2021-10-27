package br.com.bank.movements.repository;

import br.com.bank.movements.dto.Event;

public interface EventRepository {
    Event register(Event event);
}
