package dev.cele.asa_sm.ui.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.services.CommandRunnerService;
import dev.cele.asa_sm.services.SteamCMDService;
import dev.cele.asa_sm.ui.components.server_tab_accordions.AdministrationAccordion;
import dev.cele.asa_sm.ui.components.server_tab_accordions.TopPanel;
import dev.cele.asa_sm.ui.frames.ProcessFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class ServerTab extends JPanel {
    private final SteamCMDService steamCMDService = SpringApplicationContext.autoWire(SteamCMDService.class);
    private final CommandRunnerService commandRunnerService = SpringApplicationContext.autoWire(CommandRunnerService.class);
    private final ObjectMapper objectMapper = SpringApplicationContext.autoWire(ObjectMapper.class);

    private Logger log = LoggerFactory.getLogger(ServerTab.class);


    private final TopPanel topPanel;
    private Thread serverThread = null;

    private AsaServerConfigDto configDto;

    public ServerTab(AsaServerConfigDto configDto) {
        this.configDto = configDto;

        //region initial setup, var scrollPaneContent = JPanel()
        setLayout(new BorderLayout());

        GridBagConstraints globalVerticalGBC = new GridBagConstraints();
        globalVerticalGBC.fill = GridBagConstraints.HORIZONTAL;
        globalVerticalGBC.anchor = GridBagConstraints.BASELINE;
        globalVerticalGBC.gridwidth = GridBagConstraints.REMAINDER;
        globalVerticalGBC.weightx = 1.0;

        //create a JScrollPane and a content panel
        JPanel scrollPaneContent = new JPanel();
        scrollPaneContent.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(scrollPaneContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        //endregion

        //top panel
        topPanel = new TopPanel(configDto);
        scrollPaneContent.add(topPanel.contentPane, globalVerticalGBC);

        //create a group named "Administration"
        var administrationAccordion = new AdministrationAccordion(configDto);
        scrollPaneContent.add(administrationAccordion.contentPane, globalVerticalGBC);

        //... other accordion groups ...

        //create an empty filler panel that will fill the remaining space if there's any
        JPanel fillerPanel = new JPanel();
        fillerPanel.setPreferredSize(new Dimension(0, 0));
        scrollPaneContent.add(fillerPanel, gbcClone(globalVerticalGBC, gbc -> gbc.weighty = 1.0));

        detectInstalled();
    }

    private void detectInstalled(){
        //check if the server is installed
        if (Files.exists(Const.SERVERS_DIR.resolve(configDto.getGuid()))) {
            topPanel.installVerifyButton.setText("Verify/Update");
            topPanel.startButton.setEnabled(true);
        } else {
            topPanel.installVerifyButton.setText("Install");
            topPanel.startButton.setEnabled(false);
        }
    }

    public void install(){
        ProcessFrame processFrame = new ProcessFrame(
                (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this),
                steamCMDService.downloadVerifyServerCommand(configDto.getGuid()),
                result -> {
                    detectInstalled();
                }
        );
        processFrame.setVisible(true);
    }

    private void saveJson() {
        //save the configDto to a json file
        Path configFile = Path.of("data" + File.separator + "profiles" + File.separator + configDto.getGuid() + ".json");
        try {
            objectMapper.writeValue(configFile.toFile(), configDto);
            log.info("Saved configDto to " + configFile.toString() + " file");
        } catch (Exception e) {
            log.error("Error saving configDto to file", e);
        }
    }

    public void startServer() {
        //check if the server is running
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();
            topPanel.startButton.setText("Start");
            return;
        }

        //start the server
        serverThread = new Thread(() -> {
            commandRunnerService.runCommand(configDto.getCommand());
            SwingUtilities.invokeLater(() -> topPanel.startButton.setText("Start"));
        });
        serverThread.start();
        topPanel.startButton.setText("Stop");
    }

    private GridBagConstraints gbcClone(GridBagConstraints gbc) {
        return gbcClone(gbc, null);
    }
    private GridBagConstraints gbcClone(GridBagConstraints gbc, Consumer<GridBagConstraints> edit) {
        GridBagConstraints clone = (GridBagConstraints) gbc.clone();
        if(edit != null){
            edit.accept(clone);
        }
        return clone;
    }
}
