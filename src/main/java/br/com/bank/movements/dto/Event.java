package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class Event {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("type")
    private EventType eventType;
    private Integer destination;
    private BigDecimal amount;
}
