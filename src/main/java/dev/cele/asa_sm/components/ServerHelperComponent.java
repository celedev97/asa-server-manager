package dev.cele.asa_sm.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cele.asa_sm.services.CommandRunnerService;
import dev.cele.asa_sm.services.SteamCMDService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class ServerHelperComponent {
    private static ServerHelperComponent instance;
    public static ServerHelperComponent get() {
        return instance;
    }

    public final SteamCMDService steamCMDService;
    public final ObjectMapper objectMapper;
    public final CommandRunnerService commandRunnerService;

    @PostConstruct
    public void init() {
        instance = this;
    }

}
