package dev.cele.asa_sm.ui.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatClientProperties;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;
import dev.cele.asa_sm.dto.ini.IniValue;
import dev.cele.asa_sm.services.CommandRunnerService;
import dev.cele.asa_sm.services.IniSerializerService;
import dev.cele.asa_sm.services.SteamCMDService;
import dev.cele.asa_sm.ui.components.server_tab_accordions.AdministrationAccordion;
import dev.cele.asa_sm.ui.components.server_tab_accordions.RulesAccordion;
import dev.cele.asa_sm.ui.components.server_tab_accordions.TopPanel;
import dev.cele.asa_sm.ui.frames.ProcessDialog;
import lombok.Getter;
import lombok.SneakyThrows;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class ServerTab extends JPanel {

    //region Autowired stuff from spring
    private final SteamCMDService steamCMDService = SpringApplicationContext.autoWire(SteamCMDService.class);
    private final CommandRunnerService commandRunnerService = SpringApplicationContext.autoWire(CommandRunnerService.class);
    private final ObjectMapper objectMapper = SpringApplicationContext.autoWire(ObjectMapper.class);
    private final IniSerializerService iniSerializerService = SpringApplicationContext.autoWire(IniSerializerService.class);
    private Logger log = LoggerFactory.getLogger(ServerTab.class);
    //endregion

    //region UI components
    private final TopPanel topPanel;
    private final AdministrationAccordion administrationAccordion;
    private final RulesAccordion rulesAccordion;
    //endregion


    private Thread serverThread = null;
    @Getter
    private AsaServerConfigDto configDto;

    public ServerTab(AsaServerConfigDto configDto) {
        this.configDto = configDto;

        //region setup UI components
        //region initial setup, var scrollPaneContent = JPanel()
        setLayout(new BorderLayout());

        GridBagConstraints globalVerticalGBC = new GridBagConstraints();
        globalVerticalGBC.fill = GridBagConstraints.HORIZONTAL;
        globalVerticalGBC.anchor = GridBagConstraints.BASELINE;
        globalVerticalGBC.gridwidth = GridBagConstraints.REMAINDER;
        globalVerticalGBC.weightx = 1.0;
        globalVerticalGBC.insets = new Insets(5, 5, 5, 0);

        //create a JScrollPane and a content panel
        JPanel scrollPaneContent = new JPanel();
        scrollPaneContent.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(scrollPaneContent);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        //endregion

        //top panel
        topPanel = new TopPanel(configDto);
        scrollPaneContent.add(topPanel.$$$getRootComponent$$$(), globalVerticalGBC);

        //create a group named "Administration"
        administrationAccordion = new AdministrationAccordion(configDto);
        scrollPaneContent.add(administrationAccordion.$$$getRootComponent$$$(), globalVerticalGBC);

        //... other accordion groups ...
        rulesAccordion = new RulesAccordion(configDto);
        scrollPaneContent.add(rulesAccordion.$$$getRootComponent$$$(), globalVerticalGBC);

        //create an empty filler panel that will fill the remaining space if there's any
        JPanel fillerPanel = new JPanel();
        fillerPanel.setPreferredSize(new Dimension(0, 0));
        scrollPaneContent.add(fillerPanel, gbcClone(globalVerticalGBC, gbc -> gbc.weighty = 1.0));
        //endregion


        if(detectInstalled()){
            //if the server is already installed read the ini files and populate the configDto,
            //the ini files have priority so if you create a profile for an already existing server
            //the interface will be populated with the values from your existing server
            readAllIniFiles();
        }


        configDto.addUnsavedChangeListener((unsaved) -> {
            //get tabbed pane and set title with a star if unsaved
            JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, this);
            if(tabbedPane != null){
                int index = tabbedPane.indexOfComponent(this);
                if(index != -1){
                    tabbedPane.setTitleAt(index, configDto.getProfileName() + (unsaved ? " *" : ""));
                }
            }

            //set the save button outline to red if unsaved
            if(unsaved){
                topPanel.saveButton.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
            } else {
                topPanel.saveButton.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
        });

    }

    private void readAllIniFiles(){
        var gameUserSettingsIniFile = Const.SERVERS_DIR
                .resolve(configDto.getGuid())
                .resolve("ShooterGame")
                .resolve("Saved")
                .resolve("Config")
                .resolve("WindowsServer")
                .resolve("GameUserSettings.ini")
                .toFile();

        iniSerializerService.readIniFile(configDto.getGameUserSettingsINI(), gameUserSettingsIniFile);

        //TODO: read Game.ini
    }


    private boolean detectInstalled(){
        //check if the server is installed
        if (Files.exists(Const.SERVERS_DIR.resolve(configDto.getGuid()))) {
            topPanel.installVerifyButton.setText("Verify/Update");
            topPanel.openInstallLocationButton.setEnabled(true);
            topPanel.installedLocationLabel.setText(Const.SERVERS_DIR.resolve(configDto.getGuid()).toString());
            topPanel.startButton.setEnabled(true);
            return true;
        } else {
            topPanel.installVerifyButton.setText("Install");
            topPanel.openInstallLocationButton.setEnabled(false);
            topPanel.installedLocationLabel.setText("Not installed yet");
            topPanel.startButton.setEnabled(false);
            return false;
        }
    }

    public void install(){
        var wasInstalled = detectInstalled();
        ProcessDialog processDialog = new ProcessDialog(
                (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this),
                wasInstalled ? "Verifying/Updating server..." : "Installing server...",
                steamCMDService.downloadVerifyServerCommand(configDto.getGuid()),
                result -> {
                    if(!wasInstalled){
                        configDto.setUnsaved(true);
                    }
                    detectInstalled();
                }
        );
        processDialog.setVisible(true);
    }


    public boolean isServerRunning(){
        return serverThread != null && serverThread.isAlive();
    }

    public void save() {
        //save the configDto to a json file
        Path configFile = Path.of("data" + File.separator + "profiles" + File.separator + configDto.getGuid() + ".json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(configFile.toFile(), configDto);
            log.info("Saved configDto to " + configFile.toString() + " file");
            configDto.setUnsaved(false);
        } catch (Exception e) {
            log.error("Error saving configDto to file", e);
        }
    }

    public void startServer() {
        //check if the server is running
        if (isServerRunning()) {
            serverThread.interrupt();
            topPanel.startButton.setText("Start");
            return;
        }

        //start the server
        serverThread = new Thread(() -> {
            commandRunnerService.runCommand(configDto.getCommand());
            SwingUtilities.invokeLater(() ->
                    topPanel.startButton.setText("Start")
            );
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
