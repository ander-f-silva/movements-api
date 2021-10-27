package br.com.bank.movements.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Account {
    private Integer id;
    private BigDecimal balance;


}
