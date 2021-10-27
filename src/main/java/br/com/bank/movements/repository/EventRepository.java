package br.com.bank.movements.repository;

import br.com.bank.movements.dto.Event;

import java.util.List;

public interface EventRepository {
    Event register(Integer accountId, Event event);

    List<Event> listEventsByAccount(Integer id);
}
