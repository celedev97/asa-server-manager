package dev.cele.asa_sm.ui.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatClientProperties;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.services.CommandRunnerService;
import dev.cele.asa_sm.services.IniSerializerService;
import dev.cele.asa_sm.services.SteamCMDService;
import dev.cele.asa_sm.ui.components.forms.SliderWithText;
import dev.cele.asa_sm.ui.components.server_tab_accordions.*;
import dev.cele.asa_sm.ui.frames.ProcessDialog;
import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toMap;

public class ServerTab extends JPanel {

    //region Autowired stuff from spring
    private final SteamCMDService steamCMDService = SpringApplicationContext.autoWire(SteamCMDService.class);
    private final CommandRunnerService commandRunnerService = SpringApplicationContext.autoWire(CommandRunnerService.class);
    private final ObjectMapper objectMapper = SpringApplicationContext.autoWire(ObjectMapper.class);
    private final IniSerializerService iniSerializerService = SpringApplicationContext.autoWire(IniSerializerService.class);
    private Logger log = LoggerFactory.getLogger(ServerTab.class);

    private final Environment environment = SpringApplicationContext.autoWire(Environment.class);
    private final boolean isDev = Arrays.asList(environment.getActiveProfiles()).contains("dev");

    //endregion

    //region UI components
    private final JPanel scrollPaneContent;
    private final TopPanel topPanel;

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
        scrollPaneContent = new JPanel();
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
        var administrationAccordion = new AdministrationAccordion(configDto);
        scrollPaneContent.add(administrationAccordion.$$$getRootComponent$$$(), globalVerticalGBC);

        //... other accordion groups ...
        if(isDev){
            var rulesAccordion = new RulesAccordion(configDto);
            scrollPaneContent.add(rulesAccordion.$$$getRootComponent$$$(), globalVerticalGBC);
        }

        var chatAndNotificationsAccordion = new ChatAndNotificationsAccordion(configDto);
        scrollPaneContent.add(chatAndNotificationsAccordion.$$$getRootComponent$$$(), globalVerticalGBC);

        var hudAndVisualsAccordion = new HUDAndVisuals(configDto);
        scrollPaneContent.add(hudAndVisualsAccordion.$$$getRootComponent$$$(), globalVerticalGBC);

        if(isDev){
            var playerSettingsAccordion = new PlayerSettingsAccordion(configDto);
            scrollPaneContent.add(playerSettingsAccordion.$$$getRootComponent$$$(), globalVerticalGBC);
        }

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
            if(configDto.getJustImported()){

                //import extra settings from INI Files that are both on INI files and on the configDto
                configDto.setServerPassword(configDto.getGameUserSettingsINI().getServerSettings().getServerPassword());
                configDto.setServerAdminPassword(configDto.getGameUserSettingsINI().getServerSettings().getServerAdminPassword());
                configDto.setServerSpectatorPassword(configDto.getGameUserSettingsINI().getServerSettings().getSpectatorPassword());

                configDto.setServerName(configDto.getGameUserSettingsINI().getSessionSettings().getSessionName());

                configDto.setModIds(configDto.getGameUserSettingsINI().getServerSettings().getActiveMods());

                configDto.setRconEnabled(configDto.getGameUserSettingsINI().getServerSettings().getRconEnabled());
                configDto.setRconPort(configDto.getGameUserSettingsINI().getServerSettings().getRconPort());
                configDto.setRconServerLogBuffer(configDto.getGameUserSettingsINI().getServerSettings().getRconServerGameLogBuffer());
            }
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

        SwingUtilities.invokeLater(() -> {
            setupListenersForUnsaved(this);
            setupAccordionsExpansion();
        });
    }

    void setupAccordionsExpansion(){
        var accordions = new ArrayList<AccordionTopBar>();

        //loop over all the children of the container
        for (Component component : scrollPaneContent.getComponents()) {
            //if the component is a container, has a borderlayout, and his top component is a AccordionTopBar
            if(component instanceof Container &&
                    ((Container) component).getLayout() instanceof BorderLayout
            ){
                var topComponent = ((BorderLayout) ((Container) component).getLayout()).getLayoutComponent(BorderLayout.NORTH);
                if(topComponent instanceof AccordionTopBar){
                    accordions.add((AccordionTopBar) topComponent);
                }
            }
        }

        new Thread(() -> {
            try {
                Thread.sleep(1500);
                SwingUtilities.invokeLater(() -> {
                    for (var accordion : accordions) {
                        //TODO: use the text from the accordion to check if it's expanded or not and save it to a json or something?
                        accordion.setExpanded(false);
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    void setupListenersForUnsaved(Container container){
        //loop over all the children of the container
        for (Component component : container.getComponents()) {
            //if the component is a container, call this function recursively

            if(component instanceof SliderWithText){
                ((SliderWithText) component).addChangeListener(e -> configDto.setUnsaved(true));
            } else if (component instanceof JTextComponent){
                ((JTextComponent) component).getDocument().addDocumentListener(new SimpleDocumentListener(text -> configDto.setUnsaved(true)));
            } else if (component instanceof JCheckBox){
                ((JCheckBox) component).addActionListener(e -> configDto.setUnsaved(true));
            } else if (component instanceof JComboBox){
                ((JComboBox<?>) component).addActionListener(e -> configDto.setUnsaved(true));
            } else if (component instanceof JSpinner){
                ((JSpinner) component).addChangeListener(e -> configDto.setUnsaved(true));
            }else if(component instanceof Container){
                setupListenersForUnsaved((Container) component);
            }

        }
    }

    private void readAllIniFiles(){
        var gameUserSettingsIniFile = configDto.getServerPath()
                .resolve("ShooterGame")
                .resolve("Saved")
                .resolve("Config")
                .resolve("WindowsServer")
                .resolve("GameUserSettings.ini")
                .toFile();

        if(gameUserSettingsIniFile.exists()){
            iniSerializerService.readIniFile(configDto.getGameUserSettingsINI(), gameUserSettingsIniFile);
        }

        //TODO: read Game.ini
    }

    private void writeAllIniFiles(){
        var gameUserSettingsIniFile = configDto.getServerPath()
                .resolve("ShooterGame")
                .resolve("Saved")
                .resolve("Config")
                .resolve("WindowsServer")
                .resolve("GameUserSettings.ini")
                .toFile();

        iniSerializerService.writeIniFile(configDto.getGameUserSettingsINI(), gameUserSettingsIniFile);
    }


    private boolean detectInstalled(){
        //check if the server is installed
        if (Files.exists(configDto.getServerPath())) {
            topPanel.installVerifyButton.setText("Verify/Update");
            topPanel.openInstallLocationButton.setEnabled(true);
            topPanel.installedLocationLabel.setText(configDto.getServerPath().toString());
            topPanel.startButton.setEnabled(true);

            readVersionNumber();

            return true;
        } else {
            topPanel.installVerifyButton.setText("Install");
            topPanel.openInstallLocationButton.setEnabled(false);
            topPanel.installedLocationLabel.setText("Not installed yet");
            topPanel.startButton.setEnabled(false);
            return false;
        }
    }

    private void readVersionNumber() {
        try {
            //read version from log file (ShooterGame\Saved\Logs\ShooterGame.log)
            var logFile = configDto.getServerPath()
                    .resolve("ShooterGame")
                    .resolve("Saved")
                    .resolve("Logs")
                    .resolve("ShooterGame.log")
                    .toFile();

            //read file line by line, not all at once, because the file can be huge
            var bufferedReader = new BufferedReader(new FileReader(logFile));
            String line;

            var ARK_LOG_VERSION_DIVIDER = "ARK Version: ";

            while((line = bufferedReader.readLine()) != null){
                if(line.contains(ARK_LOG_VERSION_DIVIDER)){
                    var versionParts = line.split(ARK_LOG_VERSION_DIVIDER);
                    var version = "?";
                    if(versionParts.length == 2){
                        version = versionParts[1];
                    }

                    log.info("Detected version: " + version);

                    topPanel.installedVersionLabel.setText(version);
                    break;
                }
            }
            bufferedReader.close();
        }catch (Exception e){
            log.error("Error reading version from log file", e);
        }
    }

    public void install(){
        var wasInstalled = detectInstalled();
        ProcessDialog processDialog = new ProcessDialog(
                (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this),
                wasInstalled ? "Verifying/Updating server..." : "Installing server...",
                steamCMDService.downloadVerifyServerCommand(configDto.getServerPath()),
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
            log.info("Saved configDto to " + configFile + " file");
            configDto.setUnsaved(false);
            writeAllIniFiles();
        } catch (Exception e) {
            log.error("Error saving configDto to file", e);
            JOptionPane.showMessageDialog(this, "Error saving configDto to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void startServer() {
        //check if the server is running
        if (isServerRunning()) {
            serverThread.interrupt();
            topPanel.startButton.setText("Start");
            return;
        }

        if(getConfigDto().getJustImported()){
            var result = JOptionPane.showConfirmDialog(this, """
            You have just imported this server, starting it with wrong settings may damage data
            (for example check mods ids and similar stuff)
            Are you sure you checked all and you want to start it?"""
                    , "Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            configDto.setJustImported(false);
            if(result != JOptionPane.YES_OPTION){
                save();
            }
        }

        //start the server
        serverThread = new Thread(() -> {
            commandRunnerService.runCommand(configDto.getCommand());
            SwingUtilities.invokeLater(() ->
                    topPanel.startButton.setText("Start")
            );
        });

        //update the version number
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                SwingUtilities.invokeLater(() -> readVersionNumber());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

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
