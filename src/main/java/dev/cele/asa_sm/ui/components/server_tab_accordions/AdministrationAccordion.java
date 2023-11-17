package dev.cele.asa_sm.ui.components.server_tab_accordions;

import com.formdev.flatlaf.FlatClientProperties;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.AccordionTopBar;
import dev.cele.asa_sm.ui.components.forms.AutoCompleteField;
import dev.cele.asa_sm.ui.components.forms.NumberField;
import dev.cele.asa_sm.ui.frames.ModsDialog;
import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;
import lombok.NoArgsConstructor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

@NoArgsConstructor
public class AdministrationAccordion {
    private AccordionTopBar accordionTopBar;

    public JPanel contentPane;
    private JPasswordField serverPasswordField;
    private JPasswordField adminPasswordField;
    private JPasswordField spectatorPasswordField;
    private JTextField serverNameField;
    private JLabel serverNameLengthLabel;
    private NumberField serverPortField;
    private JCheckBox rconEnabledCheckBox;
    private AutoCompleteField mapNameField;
    public JTextField modIDsField;
    private JButton modSearchButton;
    private NumberField peerPortField;
    private NumberField queryPortField;
    private NumberField rconPortField;
    private NumberField rconServerLogBufferField;

    private AsaServerConfigDto configDto;

    public AdministrationAccordion(AsaServerConfigDto configDto) {
        this.configDto = configDto;
        SwingUtilities.invokeLater(this::initAfter);
    }

    void initAfter() {
        //setting initial values
        serverNameField.setText(configDto.getServerName());
        serverNameLengthLabel.setText(String.valueOf(configDto.getServerName().length()));

        serverPasswordField.setText(configDto.getServerPassword());
        serverPasswordField.putClientProperty("PasswordField.showRevealButton", true);
        adminPasswordField.setText(configDto.getServerAdminPassword());
        adminPasswordField.putClientProperty("PasswordField.showRevealButton", true);
        spectatorPasswordField.setText(configDto.getServerSpectatorPassword());
        spectatorPasswordField.putClientProperty("PasswordField.showRevealButton", true);

        serverPortField.setValue(configDto.getServerPort());
        peerPortField.setValue(configDto.getServerPort() + 1);
        queryPortField.setValue(configDto.getServerQueryPort());

        rconEnabledCheckBox.setSelected(configDto.isRconEnabled());
        rconPortField.setValue(configDto.getRconPort());
        rconServerLogBufferField.setValue(configDto.getRconServerLogBuffer());

        mapNameField.setText(configDto.getMap());
        modIDsField.setText(String.join(",", configDto.getModIds()));

        //region listeners
        serverNameField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            serverNameLengthLabel.setText(String.valueOf(text.length()));
            configDto.setServerName(text);
        }));

        serverPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setServerPassword(text);
        }));
        adminPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setServerAdminPassword(text);
        }));
        spectatorPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setServerSpectatorPassword(text);
        }));

        serverPortField.addNumberListener(val -> {
            configDto.setServerPort(val.intValue());
            peerPortField.setValue(val.intValue() + 1);
        });
        queryPortField.addNumberListener(val -> {
            configDto.setServerQueryPort(val.intValue());
        });

        rconEnabledCheckBox.addActionListener(e -> {
            configDto.setRconEnabled(rconEnabledCheckBox.isSelected());
        });
        rconPortField.addNumberListener(val -> {
            configDto.setRconPort(val.intValue());
        });
        rconServerLogBufferField.addNumberListener(val -> {
            configDto.setRconServerLogBuffer(val.intValue());
        });

        mapNameField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setMap(text);
        }));
        modIDsField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            //check if the text is all numbers and commas, if not replace all non numbers and commas with nothing
            if (!text.matches("[0-9,]*")) {
                var correctedText = text.replaceAll("[^0-9,]", "");
                SwingUtilities.invokeLater(() -> modIDsField.setText(correctedText));
                return;
            }
            configDto.setModIds(text);
        }));

        modSearchButton.addActionListener(e -> {
            //get jframe ancestor
            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this.contentPane);
            //open mod search dialog
            ModsDialog modsDialog = new ModsDialog(frame, configDto, this);
            modsDialog.setVisible(true);
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
        contentPane.setLayout(new BorderLayout(0, 0));
        accordionTopBar = new AccordionTopBar();
        accordionTopBar.setExpanded(true);
        accordionTopBar.setTitle("Administration");
        contentPane.add(accordionTopBar, BorderLayout.NORTH);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 7, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Name and Passwords", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        label1.setText("Server Name:");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Server Password:");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverPasswordField = new JPasswordField();
        panel2.add(serverPasswordField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Admin Password:");
        panel2.add(label3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adminPasswordField = new JPasswordField();
        panel2.add(adminPasswordField, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Spectator Password:");
        panel2.add(label4, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spectatorPasswordField = new JPasswordField();
        panel2.add(spectatorPasswordField, new GridConstraints(1, 5, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        serverNameField = new JTextField();
        panel2.add(serverNameField, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Length:");
        panel2.add(label5, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverNameLengthLabel = new JLabel();
        serverNameLengthLabel.setText("Label");
        panel2.add(serverNameLengthLabel, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Networking", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label6 = new JLabel();
        label6.setText("Server Port:");
        panel3.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Peer Port:");
        panel3.add(label7, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Query Port:");
        panel3.add(label8, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverPortField = new NumberField();
        panel3.add(serverPortField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        peerPortField = new NumberField();
        panel3.add(peerPortField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        queryPortField = new NumberField();
        panel3.add(queryPortField, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(null, "RCON", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        rconEnabledCheckBox = new JCheckBox();
        rconEnabledCheckBox.setText("RCON Enabled");
        panel4.add(rconEnabledCheckBox, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("RCON Port:");
        panel4.add(label9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("RCON Server Log Buffer:");
        panel4.add(label10, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rconPortField = new NumberField();
        panel4.add(rconPortField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rconServerLogBufferField = new NumberField();
        panel4.add(rconServerLogBufferField, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "Maps and Mods", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label11 = new JLabel();
        label11.setText("Map Name or Mod Map Path:");
        panel5.add(label11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mapNameField = new AutoCompleteField();
        panel5.add(mapNameField, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Mod IDs:");
        panel5.add(label12, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modIDsField = new JTextField();
        panel5.add(modIDsField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modSearchButton = new JButton();
        modSearchButton.setText("\uD83D\uDD0D");
        panel5.add(modSearchButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
