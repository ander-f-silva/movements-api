package br.com.bank.movements.repository;

import br.com.bank.movements.dto.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
class EventDao implements EventRepository {
    private Map<Integer, List<Event>> data;

    @Override
    public Event register(Integer accountId, Event event) {
        if (data.containsKey(accountId)) {
            data.get(accountId).add(event);
        } else {
            var registers = new LinkedList<Event>();
            registers.add(event);

            data.put(accountId, registers);
        }

        var count = data.get(accountId).stream().count();

        return data.get(accountId).stream().skip(count - 1).findFirst().get();
    }

    @Override
    public List<Event> listEventsByAccount(Integer accountId) {
        return data.get(accountId);
    }
}
