package br.com.bank.movements.business;

import br.com.bank.movements.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
class GettingBalance implements GetBalance {
    private final EventRepository eventRepository;

    @Override
    public Optional<BigDecimal> findByAccount(String accountId) {
        if (!eventRepository.exists(accountId)) {
            return Optional.empty();
        }

        return Optional.of(Balance.calculate(eventRepository.listEventsByAccount(accountId)));
    }
}
