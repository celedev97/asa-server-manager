package dev.cele.asa_sm.ui.components.server_tab_accordions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.ServerTab;
import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TopPanel {
    private JLabel guidLabel;
    private JButton findButton;
    private JButton importExportButton;
    private JTextField profileNameField;
    public JButton saveButton;
    public JButton openInstallLocationButton;
    public JLabel installedVersionLabel;

    public JButton startButton;
    private JButton rconButton;

    public JPanel contentPane;
    public JButton installVerifyButton;
    public JLabel installedLocationLabel;

    private final AsaServerConfigDto configDto;

    public TopPanel(AsaServerConfigDto configDto) {
        this.configDto = configDto;

        SwingUtilities.invokeLater(this::initAfter);
    }

    public void initAfter() {
        var serverTab = ((ServerTab) SwingUtilities.getAncestorOfClass(ServerTab.class, contentPane));

        guidLabel.setText(configDto.getGuid());

        profileNameField.setText(configDto.getProfileName());
        profileNameField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setProfileName(text);
        }));

        saveButton.addActionListener(e -> {
            serverTab.save();
        });

        startButton.addActionListener(e -> {
            serverTab.startServer();
        });

        installVerifyButton.addActionListener(e -> {
            serverTab.install();
        });

        openInstallLocationButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(configDto.getServerPath().toFile());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(4, 5, new Insets(5, 5, 5, 5), 5, 5));
        contentPane.putClientProperty("html.disable", Boolean.FALSE);
        final JLabel label1 = new JLabel();
        label1.setText("Profile ID:");
        contentPane.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importExportButton = new JButton();
        importExportButton.setEnabled(false);
        importExportButton.setText("Import/Export");
        contentPane.add(importExportButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        guidLabel = new JLabel();
        guidLabel.setOpaque(false);
        guidLabel.setText("{GUID-GUID-GUID}");
        contentPane.add(guidLabel, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        findButton = new JButton();
        findButton.setEnabled(false);
        findButton.setText("Find");
        contentPane.add(findButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Profile Name:");
        contentPane.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        profileNameField = new JTextField();
        contentPane.add(profileNameField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        contentPane.add(saveButton, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Installed Location:");
        contentPane.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        installedLocationLabel = new JLabel();
        installedLocationLabel.setText("servers/{GUID}");
        contentPane.add(installedLocationLabel, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openInstallLocationButton = new JButton();
        openInstallLocationButton.setText("Open Install Location");
        contentPane.add(openInstallLocationButton, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Installed Version:");
        contentPane.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        installedVersionLabel = new JLabel();
        installedVersionLabel.setText("00.0");
        contentPane.add(installedVersionLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setText("Start");
        contentPane.add(startButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rconButton = new JButton();
        rconButton.setEnabled(false);
        rconButton.setText("RCON");
        contentPane.add(rconButton, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        installVerifyButton = new JButton();
        installVerifyButton.setText("Install");
        contentPane.add(installVerifyButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}

