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

        tabbedPane.addTab("+", null);
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == tabbedPane.getTabCount() - 1) {
                SwingUtilities.invokeLater(() -> {
                    tabbedPane.setSelectedIndex(0);
                    addProfile();
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 2);
                });
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

        int index = (tabbedPane.getTabCount() > 0) ? (tabbedPane.getTabCount() - 1) : 0;
        tabbedPane.insertTab(profile.getServerName(), null, new ServerTab(profile), null, index);
    }
}
