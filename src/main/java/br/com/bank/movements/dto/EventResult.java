package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class EventResult {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Account origin;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Account destination;
}
