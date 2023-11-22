package dev.cele.asa_sm.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class MapperConfig {
    @Bean
    public ObjectMapper objectMapper(
            @Qualifier("notFailingModule") SimpleModule notFailingModule
    ) {
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

        mapper.registerModule(notFailingModule);
        mapper.registerModule(simpleLocalDateModule);

        return mapper;
    }

    @Bean(name = "notFailingModule")
    public SimpleModule notFailingModule() {
        var notFailingModule = new SimpleModule();
        notFailingModule.setSerializerModifier(new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                return beanProperties.stream().map(bpw -> new BeanPropertyWriter(bpw) {
                    @Override
                    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
                        try {
                            super.serializeAsField(bean, gen, prov);
                        } catch (Exception e) {
                            log.error(String.format("ignoring %s for field '%s' of %s instance", e.getClass().getName(), this.getName(), bean.getClass().getName()), e);
                        }
                    }
                }).collect(Collectors.toList());
            }
        });
        return notFailingModule;
    }

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();

        return mapper;
    }
}

