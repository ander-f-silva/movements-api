package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;

import java.math.BigDecimal;
import java.util.List;

class Balance {
    public static BigDecimal calculate(List<Event> events) {
        return events.
                stream().
                map(Event::getAmount).
                reduce(BigDecimal.ZERO, (previousAmount, currentAmount) -> previousAmount.add(currentAmount));
    }
}
