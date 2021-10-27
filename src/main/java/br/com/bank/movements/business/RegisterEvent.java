package br.com.bank.movements.business;

import br.com.bank.movements.dto.Event;
import br.com.bank.movements.dto.EventResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class RegisterEvent implements RegistrationEvent {

    @Qualifier("depositMovement")
    private final MovementOperation depositMovement;

    @Override
    public EventResult add(final Event event) {
        return depositMovement.effect(event);
    }
}
