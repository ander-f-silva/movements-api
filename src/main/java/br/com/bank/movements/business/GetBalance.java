package br.com.bank.movements.business;

import java.math.BigDecimal;
import java.util.Optional;

public interface GetBalance {
    Optional<BigDecimal> findByAccount(String id);
}
