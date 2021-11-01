package br.com.bank.movements.business;

import java.math.BigDecimal;

public interface GetBalance {
    BigDecimal find(Integer accountId);
}
