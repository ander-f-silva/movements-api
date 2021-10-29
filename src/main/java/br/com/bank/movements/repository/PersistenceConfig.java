package br.com.bank.movements.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class PersistenceConfig {
    @Bean
    EventRepository eventRepository() {
        return new EventDao(new HashMap<>());
    }
}
