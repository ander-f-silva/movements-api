package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class EventResult {
    @JsonIgnore
    private Account origin;
    @JsonIgnore
    private Account destination;
}
