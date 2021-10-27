package br.com.bank.movements.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
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
