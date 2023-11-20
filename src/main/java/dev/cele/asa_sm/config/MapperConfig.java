package dev.cele.asa_sm.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cele.asa_sm.services.SettingsService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();

        return mapper;
    }
}

