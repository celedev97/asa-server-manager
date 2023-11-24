package dev.cele.asa_sm.ui.components.server_tab_accordions;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.AccordionTopBar;
import dev.cele.asa_sm.ui.components.forms.AutoCompleteField;
import dev.cele.asa_sm.ui.components.forms.NumberField;
import dev.cele.asa_sm.ui.components.forms.SliderWithText;
import dev.cele.asa_sm.ui.frames.ModsDialog;
import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

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
    private JButton backupNowButton;
    private JButton restoreButton;
    private JLabel motdLinesLabel;
    private JLabel motdLenghtLabel;
    private JTextArea motdTextArea;
    private SliderWithText autoSavePeriodSlider;
    private SliderWithText backupQuantitySlider;
    private SliderWithText motdDurationSlider;
    private JCheckBox enableExtinctionEventCheckBox;
    private SliderWithText extinctionEventIntervalSlider;
    private JFormattedTextField badWordFilterUrlTextField;
    private JFormattedTextField badWordWhitelistUrlTextField;
    private JCheckBox filterTribeNamesCheckBox;
    private JCheckBox filterCharacterNamesCheckBox;
    private JCheckBox filterChatCheckBox;
    private SliderWithText maxPlayersSlider;
    private JCheckBox enableIdleTimeoutCheckBox;
    private SliderWithText idleTimeoutSlider;
    private JCheckBox useBanListURLCheckBox;
    private JCheckBox useDynamicConfigURLCheckBox;
    private JCheckBox useCustomLiveTuningCheckBox;
    private JTextField banListUrlTextField;
    private JComboBox serverLanguageComboBox;
    private JCheckBox disableValveAntiCheatCheckBox;
    private JCheckBox disableAntiSpeedHackDetectionCheckBox;
    private JCheckBox enableBattleEyeAntiCheatCheckBox;
    private JCheckBox disablePlayerMovePhysicsCheckBox;
    private JCheckBox outputServerLogToCheckBox;
    private JCheckBox noHangDetectionCheckBox;
    private JCheckBox stasisKeepControllerCheckBox;
    private JCheckBox structureMemoryOptimizationCheckBox;
    private JCheckBox structureStasisGridCheckBox;
    private JCheckBox noDinosCheckBox;
    private JCheckBox noUnderMeshCheckingCheckBox;
    private JCheckBox noUnderMeshKillingCheckBox;
    private JCheckBox allowSharedConnectionsCheckBox;
    private JCheckBox creatureUploadIssueProtectionCheckBox;
    private JCheckBox additionalDupeProtectionCheckBox;
    private JCheckBox secureItemDinoSpawningCheckBox;
    private JCheckBox forceRespawnDinosOnCheckBox;
    private JCheckBox enableAutoForceRespawnCheckBox;
    private JTextField textField2;
    private JCheckBox clusterDirectoryOverrideCheckBox;
    private JCheckBox enableServerAdminLogsCheckBox;
    private JCheckBox serverAdminLogsIncludeTribeLogsCheckBox;
    private JCheckBox serverRCONOutputTribeCheckBox;
    private JCheckBox allowHideDamageSourceCheckBox;
    private JCheckBox logAdminCommandsToPublicChatCheckBox;
    private JCheckBox logAdminCommandsToAdminChatCheckBox;
    private JCheckBox tribeLogsDestroyedEnemyCheckBox;
    private JTextField additionalServerArgsTextField;
    private JTextField dynamicConfigUrlTextField;
    private JTextField customLiveTuningUrlTextField;
    private SliderWithText forceRespawnDinoIntervalSlider;
    private JButton previewCommandButton;

    private AsaServerConfigDto configDto;

    public AdministrationAccordion(AsaServerConfigDto configDto) {
        this.configDto = configDto;
        SwingUtilities.invokeLater(this::initAfter);
    }

    void initAfter() {
        serverNameField.setText(configDto.getServerName());
        serverNameLengthLabel.setText(String.valueOf(configDto.getServerName().length()));

        serverPasswordField.setText(configDto.getServerPassword());
        serverPasswordField.putClientProperty("PasswordField.showRevealButton", true);
        adminPasswordField.setText(configDto.getServerAdminPassword());
        adminPasswordField.putClientProperty("PasswordField.showRevealButton", true);
        spectatorPasswordField.setText(configDto.getServerSpectatorPassword());
        spectatorPasswordField.putClientProperty("PasswordField.showRevealButton", true);

        serverPortField.setNumber(configDto.getServerPort());
        peerPortField.setNumber(configDto.getServerPort() + 1);
        queryPortField.setNumber(configDto.getServerQueryPort());

        rconEnabledCheckBox.setSelected(configDto.getRconEnabled());
        rconPortField.setNumber(configDto.getRconPort());
        rconServerLogBufferField.setNumber(configDto.getRconServerLogBuffer());

        mapNameField.setText(configDto.getMap());
        modIDsField.setText(String.join(",", configDto.getModIds()));

        serverNameField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            serverNameLengthLabel.setText(String.valueOf(text.length()));
            configDto.setServerName(text);
        }));

        serverPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setServerPassword(text);
            configDto.getGameUserSettingsINI().getServerSettings().setServerPassword(text);
        }));
        adminPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setServerAdminPassword(text);
            configDto.getGameUserSettingsINI().getServerSettings().setServerAdminPassword(text);
        }));
        spectatorPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setServerSpectatorPassword(text);
            configDto.getGameUserSettingsINI().getServerSettings().setSpectatorPassword(text);
        }));

        serverPortField.addNumberListener(val -> {
            configDto.setServerPort(val.intValue());
            peerPortField.setNumber(val.intValue() + 1);
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

        autoSavePeriodSlider.setMin(1);
        autoSavePeriodSlider.setStep(0.5);
        autoSavePeriodSlider.setMax(720);
        autoSavePeriodSlider.setValue(
                configDto.getGameUserSettingsINI().getServerSettings().getAutoSavePeriodMinutes()
        );
        autoSavePeriodSlider.addChangeListener(mins -> {
            configDto.getGameUserSettingsINI().getServerSettings().setAutoSavePeriodMinutes(mins.doubleValue());
        });

        backupQuantitySlider.setMin(0);
        backupQuantitySlider.setStep(1);
        backupQuantitySlider.setMax(20);
        backupQuantitySlider.setValue(configDto.getMaxBackupQuantity());
        backupQuantitySlider.addChangeListener(val -> {
            configDto.setMaxBackupQuantity(val.intValue());
        });

        motdTextArea.setText(configDto.getGameUserSettingsINI().getMessageOfTheDay().getMessage());
        motdLinesLabel.setText(String.valueOf(motdTextArea.getLineCount()));
        motdLenghtLabel.setText(String.valueOf(motdTextArea.getText().length()));

        motdTextArea.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.getGameUserSettingsINI().getMessageOfTheDay().setMessage(text);
            motdLinesLabel.setText(String.valueOf(motdTextArea.getLineCount()));
            motdLenghtLabel.setText(String.valueOf(text.length()));
        }));

        motdDurationSlider.set(1, 60, 1, configDto.getGameUserSettingsINI().getMessageOfTheDay().getDuration());
        motdDurationSlider.addChangeListener(number -> {
            configDto.getGameUserSettingsINI().getMessageOfTheDay().setDuration(number.intValue());
        });

        var secondsToDays = 60 * 60 * 24;

        enableExtinctionEventCheckBox.setSelected(configDto.getGameUserSettingsINI().getServerSettings().getExtinctionEventTimeInterval() != 0L);
        enableExtinctionEventCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setExtinctionEventTimeInterval(
                    enableExtinctionEventCheckBox.isSelected() ? extinctionEventIntervalSlider.getValue().longValue() * secondsToDays : 0L
            );
        });

        extinctionEventIntervalSlider.set(1, 1000, 1);
        if (configDto.getGameUserSettingsINI().getServerSettings().getExtinctionEventTimeInterval() == 0) {
            extinctionEventIntervalSlider.setValue(1);
        } else {
            extinctionEventIntervalSlider.setValue(configDto.getGameUserSettingsINI().getServerSettings().getExtinctionEventTimeInterval() / secondsToDays);
        }


        maxPlayersSlider.set(1, 250, 1, configDto.getServerMaxPlayers());
        maxPlayersSlider.addChangeListener(number -> {
            configDto.setServerMaxPlayers(number.intValue());
        });


        enableIdleTimeoutCheckBox.setSelected(configDto.getEnableIdlePlayerKick());
        enableIdleTimeoutCheckBox.addActionListener(e -> {
            configDto.setEnableIdlePlayerKick(enableIdleTimeoutCheckBox.isSelected());
        });

        idleTimeoutSlider.set(1, 7200, 1, configDto.getGameUserSettingsINI().getServerSettings().getKickIdlePlayersPeriod());
        idleTimeoutSlider.addChangeListener(number -> {
            configDto.getGameUserSettingsINI().getServerSettings().setKickIdlePlayersPeriod(number.doubleValue());
        });

        useBanListURLCheckBox.setSelected(!StringUtils.isEmpty(configDto.getGameUserSettingsINI().getServerSettings().getBanListURL()));
        useBanListURLCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setBanListURL(
                    useBanListURLCheckBox.isSelected() ? "http://arkdedicated.com/banlist.txt" : null
            );
        });
        banListUrlTextField.setText(configDto.getGameUserSettingsINI().getServerSettings().getBanListURL());
        banListUrlTextField.getDocument().addDocumentListener(new SimpleDocumentListener(url -> {
            configDto.getGameUserSettingsINI().getServerSettings().setBanListURL(StringUtils.isEmpty(url) ? null : url);
        }));

        useCustomLiveTuningCheckBox.setSelected(!StringUtils.isEmpty(configDto.getGameUserSettingsINI().getServerSettings().getCustomLiveTuningUrl()));
        useCustomLiveTuningCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setCustomLiveTuningUrl(
                    useCustomLiveTuningCheckBox.isSelected() ? "http://arkdedicated.com/DefaultOverloads.json" : null
            );
        });
        customLiveTuningUrlTextField.setText(configDto.getGameUserSettingsINI().getServerSettings().getCustomLiveTuningUrl());
        customLiveTuningUrlTextField.getDocument().addDocumentListener(new SimpleDocumentListener(url -> {
            configDto.getGameUserSettingsINI().getServerSettings().setCustomLiveTuningUrl(StringUtils.isEmpty(url) ? null : url);
        }));

        useDynamicConfigURLCheckBox.setSelected(!StringUtils.isEmpty(configDto.getGameUserSettingsINI().getServerSettings().getCustomDynamicConfigUrl()));
        useDynamicConfigURLCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setCustomDynamicConfigUrl(
                    useDynamicConfigURLCheckBox.isSelected() ? "http://arkdedicated.com/DynamicConfig.json" : null
            );
        });
        dynamicConfigUrlTextField.setText(configDto.getGameUserSettingsINI().getServerSettings().getCustomDynamicConfigUrl());
        dynamicConfigUrlTextField.getDocument().addDocumentListener(new SimpleDocumentListener(url -> {
            configDto.getGameUserSettingsINI().getServerSettings().setCustomDynamicConfigUrl(StringUtils.isEmpty(url) ? null : url);
        }));

        //List of currently supported language codes: ca, cs, da, de, en, es, eu, fi, fr, hu, it, ja, ka, ko, nl, pl, pt_BR, ru, sv, th, tr, zh, zh-Hans-CN, zh-TW
        var langs = List.of("", "en", "ca", "cs", "da", "de", "es", "eu", "fi", "fr", "hu", "it", "ja", "ka", "ko", "nl", "pl", "pt_BR", "ru", "sv", "th", "tr", "zh", "zh-Hans-CN", "zh-TW");
        langs.forEach(it -> serverLanguageComboBox.addItem(it));
        serverLanguageComboBox.setSelectedItem(langs.stream().filter(it -> it.equals(configDto.getCulture())).findFirst().orElse(""));
        serverLanguageComboBox.addActionListener(e -> {
            configDto.setCulture(serverLanguageComboBox.getSelectedItem().toString());
        });

        disableValveAntiCheatCheckBox.setSelected(configDto.getVacDisabled());
        disableValveAntiCheatCheckBox.addActionListener(e -> {
            configDto.setVacDisabled(disableValveAntiCheatCheckBox.isSelected());
        });

        disableAntiSpeedHackDetectionCheckBox.setSelected(configDto.getAntiSpeedHackDisabled());
        disableAntiSpeedHackDetectionCheckBox.addActionListener(e -> {
            configDto.setAntiSpeedHackDisabled(disableAntiSpeedHackDetectionCheckBox.isSelected());
        });

        enableBattleEyeAntiCheatCheckBox.setSelected(configDto.getBattlEye());
        enableBattleEyeAntiCheatCheckBox.addActionListener(e -> {
            configDto.setBattlEye(enableBattleEyeAntiCheatCheckBox.isSelected());
        });

        disablePlayerMovePhysicsCheckBox.setSelected(configDto.getDisablePlayerMovePhysicsOptimization());
        disablePlayerMovePhysicsCheckBox.addActionListener(e -> {
            configDto.setDisablePlayerMovePhysicsOptimization(disablePlayerMovePhysicsCheckBox.isSelected());
        });

//        outputServerLogToCheckBox.setSelected(configDto.isServerGameLog());
//        outputServerLogToCheckBox.addActionListener(e -> {
//            configDto.setServerGameLog(outputServerLogToCheckBox.isSelected());
//        });

        noHangDetectionCheckBox.setSelected(configDto.getDisableHangDetection());
        noHangDetectionCheckBox.addActionListener(e -> {
            configDto.setDisableHangDetection(noHangDetectionCheckBox.isSelected());
        });

        noDinosCheckBox.setSelected(configDto.getDisableDinos());
        noDinosCheckBox.addActionListener(e -> {
            configDto.setDisableDinos(noDinosCheckBox.isSelected());
        });

        noUnderMeshCheckingCheckBox.setSelected(configDto.getNoUnderMeshChecking());
        noUnderMeshCheckingCheckBox.addActionListener(e -> {
            configDto.setNoUnderMeshChecking(noUnderMeshCheckingCheckBox.isSelected());
        });

        noUnderMeshKillingCheckBox.setSelected(configDto.getNoUnderMeshKilling());
        noUnderMeshKillingCheckBox.addActionListener(e -> {
            configDto.setNoUnderMeshKilling(noUnderMeshKillingCheckBox.isSelected());
        });

        allowSharedConnectionsCheckBox.setSelected(configDto.getAllowSharedConnections());
        allowSharedConnectionsCheckBox.addActionListener(e -> {
            configDto.setAllowSharedConnections(allowSharedConnectionsCheckBox.isSelected());
        });

        creatureUploadIssueProtectionCheckBox.setSelected(configDto.getCreatureUploadIssueProtection());
        creatureUploadIssueProtectionCheckBox.addActionListener(e -> {
            configDto.setCreatureUploadIssueProtection(creatureUploadIssueProtectionCheckBox.isSelected());
        });

        additionalDupeProtectionCheckBox.setSelected(configDto.getAdditionalDupeProtection());
        additionalDupeProtectionCheckBox.addActionListener(e -> {
            configDto.setAdditionalDupeProtection(additionalDupeProtectionCheckBox.isSelected());
        });

        secureItemDinoSpawningCheckBox.setSelected(configDto.getSecureItemDinoSpawnRules());
        secureItemDinoSpawningCheckBox.addActionListener(e -> {
            configDto.setSecureItemDinoSpawnRules(secureItemDinoSpawningCheckBox.isSelected());
        });

        forceRespawnDinosOnCheckBox.setSelected(configDto.getForceRespawnDinos());
        forceRespawnDinosOnCheckBox.addActionListener(e -> {
            configDto.setForceRespawnDinos(forceRespawnDinosOnCheckBox.isSelected());
        });

        stasisKeepControllerCheckBox.setSelected(configDto.getStasisKeepControllers());
        stasisKeepControllerCheckBox.addActionListener(e -> {
            configDto.setStasisKeepControllers(stasisKeepControllerCheckBox.isSelected());
        });

        structureMemoryOptimizationCheckBox.setSelected(configDto.getStructureMemoryOptimizations());
        structureMemoryOptimizationCheckBox.addActionListener(e -> {
            configDto.setStructureMemoryOptimizations(structureMemoryOptimizationCheckBox.isSelected());
        });

        structureStasisGridCheckBox.setSelected(configDto.getUseStructureStasisGrid());
        structureStasisGridCheckBox.addActionListener(e -> {
            configDto.setUseStructureStasisGrid(structureStasisGridCheckBox.isSelected());
        });

        var secondsToHours = 60 * 60;

        enableAutoForceRespawnCheckBox.setSelected(
                configDto.getGameUserSettingsINI().getServerSettings().getServerAutoForceRespawnWildDinosInterval() != 0L
        );
        enableAutoForceRespawnCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setServerAutoForceRespawnWildDinosInterval(
                    enableAutoForceRespawnCheckBox.isSelected() ? forceRespawnDinoIntervalSlider.getValue().floatValue() * secondsToHours : 0f
            );
        });
        forceRespawnDinoIntervalSlider.set(1, 720, 1,
                configDto.getGameUserSettingsINI().getServerSettings().getServerAutoForceRespawnWildDinosInterval() == 0f
                        ? 24
                        : configDto.getGameUserSettingsINI().getServerSettings().getServerAutoForceRespawnWildDinosInterval() / secondsToDays
        );
        forceRespawnDinoIntervalSlider.addChangeListener(number -> {
            configDto.getGameUserSettingsINI().getServerSettings().setServerAutoForceRespawnWildDinosInterval(
                    number.floatValue() * secondsToHours
            );
        });

        badWordFilterUrlTextField.setText(configDto.getGameUserSettingsINI().getServerSettings().getBadWordListURL());
        badWordFilterUrlTextField.getDocument().addDocumentListener(new SimpleDocumentListener(url -> {
            configDto.getGameUserSettingsINI().getServerSettings().setBadWordListURL(StringUtils.isEmpty(url) ? null : url);
        }));

        badWordWhitelistUrlTextField.setText(configDto.getGameUserSettingsINI().getServerSettings().getBadWordWhiteListURL());
        badWordWhitelistUrlTextField.getDocument().addDocumentListener(new SimpleDocumentListener(url -> {
            configDto.getGameUserSettingsINI().getServerSettings().setBadWordWhiteListURL(StringUtils.isEmpty(url) ? null : url);
        }));

        filterTribeNamesCheckBox.setSelected(configDto.getGameUserSettingsINI().getServerSettings().getFilterTribeNames());
        filterTribeNamesCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setFilterTribeNames(filterTribeNamesCheckBox.isSelected());
        });

        filterCharacterNamesCheckBox.setSelected(configDto.getGameUserSettingsINI().getServerSettings().getFilterCharacterNames());
        filterCharacterNamesCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setFilterCharacterNames(filterCharacterNamesCheckBox.isSelected());
        });

        filterChatCheckBox.setSelected(configDto.getGameUserSettingsINI().getServerSettings().getFilterChat());
        filterChatCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setFilterChat(filterChatCheckBox.isSelected());
        });

        enableServerAdminLogsCheckBox.setSelected(configDto.getServerGameLog());
        enableServerAdminLogsCheckBox.addActionListener(e -> {
            configDto.setServerGameLog(enableServerAdminLogsCheckBox.isSelected());
        });

        serverAdminLogsIncludeTribeLogsCheckBox.setSelected(configDto.getServerGameLogIncludeTribeLogs());
        serverAdminLogsIncludeTribeLogsCheckBox.addActionListener(e -> {
            configDto.setServerGameLogIncludeTribeLogs(serverAdminLogsIncludeTribeLogsCheckBox.isSelected());
        });

        serverRCONOutputTribeCheckBox.setSelected(configDto.getServerRCONOutputTribeLogs());
        serverRCONOutputTribeCheckBox.addActionListener(e -> {
            configDto.setServerRCONOutputTribeLogs(serverRCONOutputTribeCheckBox.isSelected());
        });

        allowHideDamageSourceCheckBox.setSelected(configDto.getGameUserSettingsINI().getServerSettings().getAllowHideDamageSourceFromLogs());
        allowHideDamageSourceCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setAllowHideDamageSourceFromLogs(allowHideDamageSourceCheckBox.isSelected());
        });

        logAdminCommandsToPublicChatCheckBox.setSelected(configDto.getAdminLogsToPublicChat());
        logAdminCommandsToPublicChatCheckBox.addActionListener(e -> {
            configDto.setAdminLogsToPublicChat(logAdminCommandsToPublicChatCheckBox.isSelected());
        });

        logAdminCommandsToAdminChatCheckBox.setSelected(configDto.getAdminLogsToAdminChat());
        logAdminCommandsToAdminChatCheckBox.addActionListener(e -> {
            configDto.setAdminLogsToAdminChat(logAdminCommandsToAdminChatCheckBox.isSelected());
        });

        tribeLogsDestroyedEnemyCheckBox.setSelected(configDto.getGameUserSettingsINI().getServerSettings().getTribeLogDestroyedEnemyStructures());
        tribeLogsDestroyedEnemyCheckBox.addActionListener(e -> {
            configDto.getGameUserSettingsINI().getServerSettings().setTribeLogDestroyedEnemyStructures(tribeLogsDestroyedEnemyCheckBox.isSelected());
        });

        additionalServerArgsTextField.setText(configDto.getAdditionalServerArgs());
        additionalServerArgsTextField.getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            configDto.setAdditionalServerArgs(text);
        }));

        previewCommandButton.addActionListener(e -> {
            //get jframe ancestor
            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this.contentPane);
            //get command
            var command = configDto.getCommand();
            //show dialog
            JOptionPane.showMessageDialog(frame,
                    String.join("\r\n", command) + "\r\n" + configDto.getAdditionalServerArgs(),
                    "Command",
                    JOptionPane.INFORMATION_MESSAGE
            );
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
        panel1.setLayout(new GridLayoutManager(11, 1, new Insets(0, 0, 0, 0), -1, -1));
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
        peerPortField.setEditable(false);
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
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(null, "Saves", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label13 = new JLabel();
        label13.setText("Auto Save Period");
        panel6.add(label13, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoSavePeriodSlider = new SliderWithText();
        panel6.add(autoSavePeriodSlider, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("minutes");
        panel6.add(label14, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backupNowButton = new JButton();
        backupNowButton.setEnabled(false);
        backupNowButton.setText("Backup Now");
        panel6.add(backupNowButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restoreButton = new JButton();
        restoreButton.setEnabled(false);
        restoreButton.setText("Restore");
        panel6.add(restoreButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Backup Quantity");
        panel6.add(label15, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backupQuantitySlider = new SliderWithText();
        panel6.add(backupQuantitySlider, new GridConstraints(1, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(3, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(null, "Message of The Day", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label16 = new JLabel();
        label16.setText("Lines: ");
        panel7.add(label16, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        motdLinesLabel = new JLabel();
        motdLinesLabel.setText("0");
        panel7.add(motdLinesLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Length: ");
        panel7.add(label17, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        motdLenghtLabel = new JLabel();
        motdLenghtLabel.setText("0");
        panel7.add(motdLenghtLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel7.add(spacer1, new GridConstraints(0, 4, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        motdTextArea = new JTextArea();
        panel7.add(motdTextArea, new GridConstraints(1, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("Duration");
        panel7.add(label18, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        motdDurationSlider = new SliderWithText();
        panel7.add(motdDurationSlider, new GridConstraints(2, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("seconds");
        panel7.add(label19, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel8, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(null, "Extinction Event", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        enableExtinctionEventCheckBox = new JCheckBox();
        enableExtinctionEventCheckBox.setText("Enable Extinction Event");
        panel8.add(enableExtinctionEventCheckBox, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Time Interval");
        panel8.add(label20, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extinctionEventIntervalSlider = new SliderWithText();
        panel8.add(extinctionEventIntervalSlider, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel9, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(null, "Server Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel9.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("Max Players");
        panel10.add(label21, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        maxPlayersSlider = new SliderWithText();
        panel10.add(maxPlayersSlider, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        enableIdleTimeoutCheckBox = new JCheckBox();
        enableIdleTimeoutCheckBox.setText("Enable Idle Timeout");
        panel10.add(enableIdleTimeoutCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        idleTimeoutSlider = new SliderWithText();
        panel10.add(idleTimeoutSlider, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel9.add(panel11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        useBanListURLCheckBox = new JCheckBox();
        useBanListURLCheckBox.setText("Use Ban List URL");
        panel11.add(useBanListURLCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        useDynamicConfigURLCheckBox = new JCheckBox();
        useDynamicConfigURLCheckBox.setText("Use Dynamic Config URL");
        panel11.add(useDynamicConfigURLCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        useCustomLiveTuningCheckBox = new JCheckBox();
        useCustomLiveTuningCheckBox.setText("Use Custom Live Tuning URL");
        panel11.add(useCustomLiveTuningCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        banListUrlTextField = new JTextField();
        panel11.add(banListUrlTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dynamicConfigUrlTextField = new JTextField();
        panel11.add(dynamicConfigUrlTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        customLiveTuningUrlTextField = new JTextField();
        panel11.add(customLiveTuningUrlTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Server Language");
        panel11.add(label22, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverLanguageComboBox = new JComboBox();
        panel11.add(serverLanguageComboBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(14, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel9.add(panel12, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        disableValveAntiCheatCheckBox = new JCheckBox();
        disableValveAntiCheatCheckBox.setText("Disable Valve Anti-Cheat System (VAC)");
        panel12.add(disableValveAntiCheatCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enableBattleEyeAntiCheatCheckBox = new JCheckBox();
        enableBattleEyeAntiCheatCheckBox.setText("Enable BattleEye Anti-Cheat System");
        panel12.add(enableBattleEyeAntiCheatCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel12.add(panel13, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final SliderWithText sliderWithText1 = new SliderWithText();
        panel13.add(sliderWithText1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("x");
        panel13.add(label23, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableAntiSpeedHackDetectionCheckBox = new JCheckBox();
        disableAntiSpeedHackDetectionCheckBox.setText("Disable Anti-SpeedHack Detection");
        panel12.add(disableAntiSpeedHackDetectionCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disablePlayerMovePhysicsCheckBox = new JCheckBox();
        disablePlayerMovePhysicsCheckBox.setText("Disable Player-Move-Physics Optimization");
        panel12.add(disablePlayerMovePhysicsCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputServerLogToCheckBox = new JCheckBox();
        outputServerLogToCheckBox.setText("Output Server Log to Server Console");
        panel12.add(outputServerLogToCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noHangDetectionCheckBox = new JCheckBox();
        noHangDetectionCheckBox.setText("No Hang Detection");
        panel12.add(noHangDetectionCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noDinosCheckBox = new JCheckBox();
        noDinosCheckBox.setText("No Dinos");
        panel12.add(noDinosCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        allowSharedConnectionsCheckBox = new JCheckBox();
        allowSharedConnectionsCheckBox.setText("Allow Shared Connections");
        panel12.add(allowSharedConnectionsCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        creatureUploadIssueProtectionCheckBox = new JCheckBox();
        creatureUploadIssueProtectionCheckBox.setText("Creature Upload Issue Protection");
        panel12.add(creatureUploadIssueProtectionCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        additionalDupeProtectionCheckBox = new JCheckBox();
        additionalDupeProtectionCheckBox.setText("Additional Dupe Protection");
        panel12.add(additionalDupeProtectionCheckBox, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        secureItemDinoSpawningCheckBox = new JCheckBox();
        secureItemDinoSpawningCheckBox.setText("Secure Item/Dino Spawning Rules");
        panel12.add(secureItemDinoSpawningCheckBox, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        forceRespawnDinosOnCheckBox = new JCheckBox();
        forceRespawnDinosOnCheckBox.setText("Force Respawn Dinos on Startup (Cause Lag after Ready when repopulating)");
        panel12.add(forceRespawnDinosOnCheckBox, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enableAutoForceRespawnCheckBox = new JCheckBox();
        enableAutoForceRespawnCheckBox.setText("Enable Auto Force Respawn Dinos");
        panel12.add(enableAutoForceRespawnCheckBox, new GridConstraints(11, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        forceRespawnDinoIntervalSlider = new SliderWithText();
        panel12.add(forceRespawnDinoIntervalSlider, new GridConstraints(12, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel12.add(panel14, new GridConstraints(13, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Alternate Directory Save Name");
        panel14.add(label24, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        panel14.add(textField2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("NOTE: Alternate Directory is usually used for advanced cluster setup, don't use it unless you know what you're doing");
        panel14.add(label25, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setText("Cross-Ark Data Cluster ID:");
        panel14.add(label26, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setText("(Optional)");
        panel14.add(label27, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JTextField textField1 = new JTextField();
        panel14.add(textField1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        clusterDirectoryOverrideCheckBox = new JCheckBox();
        clusterDirectoryOverrideCheckBox.setText("Cluster Directory Override");
        panel14.add(clusterDirectoryOverrideCheckBox, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        structureMemoryOptimizationCheckBox = new JCheckBox();
        structureMemoryOptimizationCheckBox.setText("Structure Memory Optimization");
        panel12.add(structureMemoryOptimizationCheckBox, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        structureStasisGridCheckBox = new JCheckBox();
        structureStasisGridCheckBox.setText("Structure Stasis Grid");
        panel12.add(structureStasisGridCheckBox, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noUnderMeshCheckingCheckBox = new JCheckBox();
        noUnderMeshCheckingCheckBox.setText("No Under Mesh Checking");
        panel12.add(noUnderMeshCheckingCheckBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noUnderMeshKillingCheckBox = new JCheckBox();
        noUnderMeshKillingCheckBox.setText("No Under Mesh Killing");
        panel12.add(noUnderMeshKillingCheckBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stasisKeepControllerCheckBox = new JCheckBox();
        stasisKeepControllerCheckBox.setText("Stasis Keep Controller");
        panel12.add(stasisKeepControllerCheckBox, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel15, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel15.setBorder(BorderFactory.createTitledBorder(null, "Bad Word Filter", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label28 = new JLabel();
        label28.setText("Bad Word Filter URL:");
        panel15.add(label28, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label29 = new JLabel();
        label29.setText("Bad Word Whitelist URL:");
        panel15.add(label29, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        badWordFilterUrlTextField = new JFormattedTextField();
        panel15.add(badWordFilterUrlTextField, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        badWordWhitelistUrlTextField = new JFormattedTextField();
        panel15.add(badWordWhitelistUrlTextField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel16 = new JPanel();
        panel16.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel15.add(panel16, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        filterTribeNamesCheckBox = new JCheckBox();
        filterTribeNamesCheckBox.setText("Filter Tribe Names");
        panel16.add(filterTribeNamesCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filterCharacterNamesCheckBox = new JCheckBox();
        filterCharacterNamesCheckBox.setText("Filter Character Names");
        panel16.add(filterCharacterNamesCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filterChatCheckBox = new JCheckBox();
        filterChatCheckBox.setText("Filter Chat");
        panel16.add(filterChatCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel17 = new JPanel();
        panel17.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel17, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel17.setBorder(BorderFactory.createTitledBorder(null, "Server Log Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        enableServerAdminLogsCheckBox = new JCheckBox();
        enableServerAdminLogsCheckBox.setText("Enable Server Admin Logs");
        panel17.add(enableServerAdminLogsCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverAdminLogsIncludeTribeLogsCheckBox = new JCheckBox();
        serverAdminLogsIncludeTribeLogsCheckBox.setText("Server Admin Logs Include Tribe Logs");
        panel17.add(serverAdminLogsIncludeTribeLogsCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        serverRCONOutputTribeCheckBox = new JCheckBox();
        serverRCONOutputTribeCheckBox.setText("Server RCON Output Tribe Logs");
        panel17.add(serverRCONOutputTribeCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        allowHideDamageSourceCheckBox = new JCheckBox();
        allowHideDamageSourceCheckBox.setText("Allow Hide Damage Source From Logs");
        panel17.add(allowHideDamageSourceCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logAdminCommandsToPublicChatCheckBox = new JCheckBox();
        logAdminCommandsToPublicChatCheckBox.setText("Log Admin Commands To Chat (public)");
        panel17.add(logAdminCommandsToPublicChatCheckBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logAdminCommandsToAdminChatCheckBox = new JCheckBox();
        logAdminCommandsToAdminChatCheckBox.setText("Log Admin Commands To Chat (administrators only)");
        panel17.add(logAdminCommandsToAdminChatCheckBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tribeLogsDestroyedEnemyCheckBox = new JCheckBox();
        tribeLogsDestroyedEnemyCheckBox.setText("Tribe Logs Destroyed Enemy Structure");
        panel17.add(tribeLogsDestroyedEnemyCheckBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel18 = new JPanel();
        panel18.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel17.add(panel18, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final NumberField numberField1 = new NumberField();
        panel18.add(numberField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label30 = new JLabel();
        label30.setText("Maximum Tribe Logs");
        panel18.add(label30, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel19 = new JPanel();
        panel19.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel19, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel19.setBorder(BorderFactory.createTitledBorder(null, "Command Line", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label31 = new JLabel();
        label31.setText("Additional Server Args:");
        panel19.add(label31, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        additionalServerArgsTextField = new JTextField();
        panel19.add(additionalServerArgsTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        previewCommandButton = new JButton();
        previewCommandButton.setText("Preview Command");
        panel19.add(previewCommandButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
