package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(value = {"origin, destination"})
public class EventResult {
    private Account origin;

    private Account destination;
}
