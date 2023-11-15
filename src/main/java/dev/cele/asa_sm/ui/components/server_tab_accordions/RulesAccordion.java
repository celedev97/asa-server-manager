package dev.cele.asa_sm.ui.components.server_tab_accordions;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.AccordionTopBar;

import javax.swing.*;
import java.awt.*;

public class RulesAccordion {
    private JCheckBox enableHardcoreModeCheckBox;
    private JCheckBox enablePvPCheckBox;
    private JCheckBox enableCreativeModeCheckBox;
    public JPanel contentPane;

    public RulesAccordion(AsaServerConfigDto configDto) {
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, BorderLayout.CENTER);
        enableHardcoreModeCheckBox = new JCheckBox();
        enableHardcoreModeCheckBox.setText("Enable Hardcore Mode");
        panel1.add(enableHardcoreModeCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enablePvPCheckBox = new JCheckBox();
        enablePvPCheckBox.setText("Enable PvP");
        panel1.add(enablePvPCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enableCreativeModeCheckBox = new JCheckBox();
        enableCreativeModeCheckBox.setText("Enable Creative Mode");
        panel1.add(enableCreativeModeCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final AccordionTopBar accordionTopBar1 = new AccordionTopBar();
        accordionTopBar1.setExpanded(true);
        accordionTopBar1.setTitle("Rules");
        contentPane.add(accordionTopBar1, BorderLayout.NORTH);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
