package br.com.bank.movements.business;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
class GettingBalance implements GetBalance {
    @Override
    public BigDecimal find(Integer accountId) {
        return null;
    }
}
