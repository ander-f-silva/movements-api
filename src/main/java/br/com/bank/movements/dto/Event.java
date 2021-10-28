package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("type")
    private EventType eventType;
    @JsonIgnoreProperties
    private Integer destination;
    @JsonIgnoreProperties
    private Integer origin;
    private BigDecimal amount;
}
