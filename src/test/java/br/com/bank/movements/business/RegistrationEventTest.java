package br.com.bank.movements.business;


import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class RegistrationEventTest {
    private RegistrationEvent registrationEvent;

    @Test
    @DisplayName("should add an event")
    void testAddAnEvent() {
        var fakeAccountId = 100;
        var fakeAmount = new BigDecimal(10);
        var fakeEvent = new Event(EventType.DEPOSIT, fakeAccountId, fakeAmount);

        var result = registrationEvent.add(fakeEvent);

    }

}
