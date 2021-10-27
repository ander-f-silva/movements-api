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
    public Event register(Event event) {
        if (data.containsKey(event.getDestination())) {
            data.get(event.getDestination()).add(event);
        } else {
            var registers = new LinkedList<Event>();
            registers.add(event);

            data.put(event.getDestination(), registers);
        }

        var count = data.get(event.getDestination()).stream().count();

        return data.get(event.getDestination()).stream().skip(count - 1).findFirst().get();
    }
}
