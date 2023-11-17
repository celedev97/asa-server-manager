package dev.cele.asa_sm.ui.frames;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.services.UpdateService;
import dev.cele.asa_sm.ui.components.ServerTab;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

@Slf4j
public class MainFrame extends JFrame {

    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final ObjectMapper objectMapper = SpringApplicationContext.autoWire(ObjectMapper.class);
    private final UpdateService updateService = SpringApplicationContext.autoWire(UpdateService.class);

    @SneakyThrows
    public MainFrame() {
        setTitle("ASA Server Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(825, 600));
        setSize(new Dimension(1020, 890));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        var iconResource = new ClassPathResource("icon.png");
        setIconImage(
                new ImageIcon(iconResource.getContentAsByteArray()).getImage()
        );


        try {
            Files.createDirectories(Const.PROFILES_DIR);
        } catch (IOException e) {
            log.error("Error creating profiles directory", e);
        }

        // Look for json files in the profiles directory
        List<AsaServerConfigDto> profiles = new ArrayList<>();
        try {
            profiles = Files.list(Const.PROFILES_DIR)
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(path -> {
                        try {
                            return objectMapper.readValue(path.toFile(), AsaServerConfigDto.class);
                        } catch (IOException e) {
                            log.error("Error reading profile file: " + path.getFileName(), e);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            log.error("Error listing profile files", e);
        }

        if (profiles.isEmpty()) {
            // If there are no profiles, create a new one
            addProfile();
        } else {
            profiles.forEach(this::addProfile);
        }

        add(tabbedPane, BorderLayout.CENTER);

        //create the trailing toolbar with the plus button
        var plusButton = new JButton("+");
        plusButton.addActionListener(e -> {
            addProfile();
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        });

        var trailing = new JToolBar();
        trailing.setFloatable(false);
        trailing.setBorder(null);
        trailing.add(Box.createHorizontalGlue());
        trailing.add(plusButton);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TRAILING_COMPONENT, trailing);
        tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, true);
        tabbedPane.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK,(IntConsumer) tabIndex -> {
            // get the tab component
            var tabComponent = (ServerTab) tabbedPane.getComponentAt(tabIndex);

            if(tabComponent.isServerRunning()){
                JOptionPane.showMessageDialog(this, "Server is running, please stop it first", "Server is running", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(tabComponent.getConfigDto().isUnsaved()){
                int result = JOptionPane.showConfirmDialog(this, "You have unsaved changes, do you want to save them?", "Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    tabComponent.save();
                }else if(result == JOptionPane.CANCEL_OPTION){
                    return;
                }
            }
            tabbedPane.removeTabAt(tabIndex);
            if(tabbedPane.getTabCount() == 0){
                addProfile();
            }
        });

        //region menu bar
        var menuBar = new JMenuBar();

        //region file menu
        var fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        var newProfileMenuItem = new JMenuItem("New Profile");
        newProfileMenuItem.setMnemonic('N');
        newProfileMenuItem.addActionListener(e -> addProfile());
        fileMenu.add(newProfileMenuItem);

        var openProfileMenuItem = new JMenuItem("Open Profile");
        openProfileMenuItem.setMnemonic('O');

        var openProfileFileChooser = new JFileChooser();
        openProfileFileChooser.setCurrentDirectory(Const.PROFILES_DIR.toFile());
        openProfileFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openProfileFileChooser.setFileFilter(new FileNameExtensionFilter("ASA Server Manager Profile", "json"));

        openProfileMenuItem.addActionListener(e -> {
            var result = openProfileFileChooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
                try {
                    var profile = objectMapper.readValue(openProfileFileChooser.getSelectedFile(), AsaServerConfigDto.class);
                    //check that profile is not already opened
                    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                        var tab = (ServerTab) tabbedPane.getComponentAt(i);
                        if(tab.getConfigDto().getGuid().equals(profile.getGuid())){
                            tabbedPane.setSelectedIndex(i);
                            return;
                        }
                    }
                    addProfile(profile);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(this, "Error reading profile file: " + openProfileFileChooser.getSelectedFile().getName(), "Error reading file", JOptionPane.ERROR_MESSAGE);
                    log.error("Error reading profile file: ", ioException);
                }
            }
        });
        fileMenu.add(openProfileMenuItem);

        var saveProfileMenuItem = new JMenuItem("Save Profile");
        saveProfileMenuItem.setMnemonic('S');
        saveProfileMenuItem.addActionListener(e -> {
            var selectedTab = (ServerTab) tabbedPane.getSelectedComponent();
            selectedTab.save();
        });
        fileMenu.add(saveProfileMenuItem);

        fileMenu.addSeparator();

        var exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic('x');
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        //endregion

        //region settings menu
        var settingsMenu = new JMenu("Settings");
        settingsMenu.setMnemonic('S');

        var settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.setMnemonic('S');
        settingsMenuItem.addActionListener(e -> {
            var settingsFrame = new SettingsFrame(this);
            settingsFrame.setVisible(true);
        });
        settingsMenu.add(settingsMenuItem);

        menuBar.add(settingsMenu);
        //endregion

        //region help menu
        var helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        var aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setMnemonic('A');
        aboutMenuItem.addActionListener(e -> {
            var aboutFrame = new AboutFrame(this);
            aboutFrame.setVisible(true);
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);
        //endregion

        setJMenuBar(menuBar);
        //endregion

        //region status bar
        var statusBar = new JPanel();
        statusBar.setLayout(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEtchedBorder());

        var statusLabel = new JLabel("ASA Server Manager");
        statusBar.add(statusLabel, BorderLayout.WEST);

        var versionLabel = new JLabel("v" + updateService.getCurrentVersion());
        statusBar.add(versionLabel, BorderLayout.EAST);

        add(statusBar, BorderLayout.SOUTH);
        //endregion
    }

    private void addProfile() {
        addProfile(null);
    }

    private void addProfile(AsaServerConfigDto input) {
        AsaServerConfigDto profile = (input != null) ? input : new AsaServerConfigDto();
        if (input == null) {
            profile.setServerName("Default");
        }

        int index = Math.max(tabbedPane.getTabCount(), 0);
        tabbedPane.insertTab(profile.getProfileName(), null, new ServerTab(profile), null, index);
    }
}
