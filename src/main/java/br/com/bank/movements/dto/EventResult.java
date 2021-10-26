package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventResult {
    @JsonProperty("origin")
    private Account origin;
}
