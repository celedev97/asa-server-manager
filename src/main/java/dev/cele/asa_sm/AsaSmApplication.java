package dev.cele.asa_sm;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.services.SteamCMDService;
import dev.cele.asa_sm.services.UpdateService;
import dev.cele.asa_sm.ui.frames.MainFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SpringBootApplication
@EnableFeignClients("dev.cele.asa_sm")
@Slf4j
public class AsaSmApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private SteamCMDService steamCMDService;

    //this is needed to get the application context in the static main method
    @Autowired
    private SpringApplicationContext springApplicationContext;

    public static void main(String[] args) {
        new SpringApplicationBuilder(AsaSmApplication.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);
    }


    @Override
    public void run(String... args) {

        FlatLaf.registerCustomDefaultsSource( "dev.cele.asa_sm.themes" );
        IntelliJTheme.setup( AsaSmApplication.class.getResourceAsStream("/nord.theme.json") );

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
