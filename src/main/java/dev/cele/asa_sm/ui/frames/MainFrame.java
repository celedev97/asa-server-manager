package dev.cele.asa_sm.ui.frames;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.ServerTab;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
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

    public MainFrame() {
        setTitle("ASA Server Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(825, 600));
        setMinimumSize(new Dimension(825, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        plusButton.putClientProperty( "FlatLaf.styleClass", "large" );
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
        tabbedPane.putClientProperty("JTabbedPane.trailingComponent", trailing);
        tabbedPane.putClientProperty("JTabbedPane.tabClosable", true);
        tabbedPane.putClientProperty( "JTabbedPane.tabCloseCallback",(IntConsumer) tabIndex -> {
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

        pack();
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
