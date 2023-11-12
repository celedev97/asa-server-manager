package dev.cele.asa_sm.ui.components.server_tab_accordions;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.ServerTab;
import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;

import javax.swing.*;
import java.io.IOException;

public class TopPanel {
    private JLabel guidLabel;
    private JButton findButton;
    private JButton importExportButton;
    private JTextField profileNameField;
    private JButton saveButton;
    private JButton openInstallLocationButton;
    private JLabel installedVersionLabel;

    public JButton startButton;
    private JButton rconButton;

    public JPanel contentPane;

    private final ObjectMapper objectMapper = SpringApplicationContext.autoWire(ObjectMapper.class);


    private final AsaServerConfigDto configDto;

    public TopPanel(AsaServerConfigDto configDto) {
        this.configDto = configDto;

        SwingUtilities.invokeLater(() -> {
            guidLabel.setText(configDto.getGuid());

            profileNameField.setText(configDto.getProfileName());
            profileNameField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
                configDto.setProfileName(text);
            }));

            saveButton.addActionListener(e -> {
                try {
                    objectMapper.writeValue(Const.PROFILES_DIR.resolve(configDto.getGuid() + ".json").toFile(), configDto);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Failed to save profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            startButton.addActionListener(e -> {
                ((ServerTab) SwingUtilities.getAncestorOfClass(ServerTab.class, contentPane)).startServer();
            });
        });

    }

}

