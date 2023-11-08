package dev.cele.asa_sm.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MapperConfig {
    @Bean
    open fun modelMapper(): ObjectMapper {
        return ObjectMapper()
    }
}
