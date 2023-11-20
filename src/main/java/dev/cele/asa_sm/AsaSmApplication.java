package dev.cele.asa_sm;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.services.SettingsService;
import dev.cele.asa_sm.services.SteamCMDService;
import dev.cele.asa_sm.services.UpdateService;
import dev.cele.asa_sm.ui.frames.MainFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SpringBootApplication
@EnableFeignClients("dev.cele.asa_sm")
@Slf4j
@RequiredArgsConstructor
public class AsaSmApplication implements CommandLineRunner {

    @Autowired @Lazy
    private ApplicationContext appContext;

    private final UpdateService updateService;
    private final SteamCMDService steamCMDService;
    private final SettingsService settingsService;

    //this is needed since the beans are initialized lazily,
    // and this bean is needed for the UI to have access to the beans
    private final SpringApplicationContext springApplicationContext;

    public static void main(String[] args) {
        new SpringApplicationBuilder(AsaSmApplication.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);
    }


    @Override
    public void run(String... args) {

        FlatLaf.registerCustomDefaultsSource( "dev.cele.asa_sm.themes" );
        settingsService.loadTheme();

        new Thread(() -> {
            updateService.checkForUpdates();
        }).start();

        new Thread(() -> {
            steamCMDService.checkSteamCMD();
        }).start();


        var frame = new MainFrame();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e)
            {
                log.info("Closing ASA Server Manager");
                SpringApplication.exit(appContext, () -> 0);
            }
        });
    }

}
