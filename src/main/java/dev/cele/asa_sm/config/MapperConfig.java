package dev.cele.asa_sm.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class MapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        var yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        var simpleLocalDateModule = new SimpleModule(){{
            addSerializer(LocalDate.class, new JsonSerializer<>() {
                @Override
                public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                    jsonGenerator.writeString(yyyyMMdd.format(localDate));
                }
            });

            addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                    return LocalDate.parse(jsonParser.getText(), yyyyMMdd);
                }
            });
        }};

        mapper.registerModule(simpleLocalDateModule);

        return mapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();

        return mapper;
    }
}

