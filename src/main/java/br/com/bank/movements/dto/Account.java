package br.com.bank.movements.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public class Account {
    private String id;
    private BigDecimal balance;
}
