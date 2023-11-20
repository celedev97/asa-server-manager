package dev.cele.asa_sm.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import dev.cele.asa_sm.AsaSmApplication;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.dto.SettingsDto;
import jakarta.annotation.PostConstruct;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
@Slf4j
public class SettingsService {

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Getter
    private final SettingsDto settings = new SettingsDto();

    public SettingsService(ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;

        if(Const.SETTINGS_FILE.toFile().exists()){
            try {
                var deserializedSettings = objectMapper.readValue(Const.SETTINGS_FILE.toFile(), SettingsDto.class);
                modelMapper.map(deserializedSettings, settings);
            } catch (Exception e) {
                log.error("Error loading settings", e);
            }
        }

    }


    @SneakyThrows
    public void save(){
        objectMapper.writeValue(Const.SETTINGS_FILE.toFile(), settings);
        loadTheme();
    }

    @SneakyThrows
    public void loadTheme(){
        log.info("LOADING THEME: {}", settings.getTheme());

        //check if theme starts with classpath
        if(settings.getTheme().startsWith("classpath:")){
            //remove classpath
            var themePath = settings.getTheme().substring("classpath:".length());
            //load theme from classpath
            IntelliJTheme.setup(AsaSmApplication.class.getResourceAsStream(themePath));
        }else{
            //load theme from file
            IntelliJTheme.setup(new FileInputStream(Const.THEME_DIR.resolve(settings.getTheme()).toFile()));
        }

        FlatLaf.updateUI();
    }

}
