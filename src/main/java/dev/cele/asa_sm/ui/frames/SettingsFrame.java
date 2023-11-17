package dev.cele.asa_sm.ui.frames;

import javax.swing.*;

public class SettingsFrame extends JDialog {

    public SettingsFrame(JFrame owner){
        super(owner, "Settings", true);

        add(new JLabel("Work in progress..."));

        pack();
        setLocationRelativeTo(owner);
    }

}
