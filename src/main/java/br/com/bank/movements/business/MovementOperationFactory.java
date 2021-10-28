package br.com.bank.movements.business;

import br.com.bank.movements.dto.EventType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@AllArgsConstructor
public class MovementOperationFactory {
    @Qualifier("depositMovement")
    private final MovementOperation depositMovement;

    @Qualifier("withdrawMovement")
    private final MovementOperation withdrawMovement;

    private Map<EventType, MovementOperation> movementOperation;

    @PostConstruct
    public void init() {
        movementOperation.put(EventType.DEPOSIT, depositMovement);
        movementOperation.put(EventType.WITHDRAW, withdrawMovement);
    }

    public MovementOperation get(EventType type) {
        return movementOperation.get(type);
    }
}
