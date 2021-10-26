package br.com.bank.movements.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class Account {
    private Integer id;
    private BigDecimal balance;
}
