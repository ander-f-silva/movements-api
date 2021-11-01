package br.com.bank.movements.repository;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventType;
import br.com.bank.movements.repository.EventRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockEventRepository implements EventRepository {
    private Map<String, List<Event>> mockData = new HashMap<>();

    public MockEventRepository(String accountId, List<Event> list) {
        mockData.put(accountId, list);
    }

    @Override
    public Event register(String accountId, Event event) {
        if (mockData.containsKey(accountId)) {
            mockData.get(accountId).add(event);
        } else {
            var list = new ArrayList<Event>();
            list.add(event);

            mockData.put(accountId, list);
        }

        return event;
    }

    @Override
    public List<Event> listEventsByAccount(String id) {
        return mockData.get(id);
    }

    @Override
    public boolean exists(String accountId) {
        return mockData.containsKey(accountId);
    }

    @Override
    public void clear() {
        mockData.clear();
    }
}
