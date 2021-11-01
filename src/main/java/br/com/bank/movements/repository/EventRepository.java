package br.com.bank.movements.repository;

import br.com.bank.movements.dto.Event;

import java.util.List;

public interface EventRepository {
    Event register(String accountId, Event event);

    List<Event> listEventsByAccount(String id);

    boolean exists(String accountId);

    void clear();
}
