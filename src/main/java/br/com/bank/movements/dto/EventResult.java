package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResult {
    @JsonProperty("origin")
    private Account origin;
}
