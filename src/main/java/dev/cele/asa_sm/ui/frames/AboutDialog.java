package dev.cele.asa_sm.ui.frames;

import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.services.UpdateService;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {

    private UpdateService updateService = SpringApplicationContext.autoWire(UpdateService.class);

    public AboutDialog(Frame owner) {
        super(owner, "About", true);

        setLayout(new GridBagLayout());

        var IMAGE_SIZE = 128;

        var image = owner.getIconImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
        var logoLabel = new JLabel(new ImageIcon(image));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        add(logoLabel, c);
        add(new JLabel("""
                <html>
                Asa Server Manager, version <b>%s</b><br>
                <br>
                Created by <b>celedev97</b><br>
                </html>
                """.formatted(updateService.getCurrentVersion())
        ), c);

        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }
}
