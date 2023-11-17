package dev.cele.asa_sm.ui.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatBorder;
import com.formdev.flatlaf.ui.FlatRoundBorder;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccordionTopBar extends JPanel {
    private final JLabel label = new JLabel();

    @Getter
    private boolean expanded = false;
    public void setExpanded(boolean value) {
        expanded = value;
        expandButton.setText(value ? "˄" : "˅");

        Container parent = getParent();
        if (parent != null && parent.getLayout() instanceof BorderLayout) {
            Component centerComponent = ((BorderLayout) parent.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            if (centerComponent != null) {
                centerComponent.setVisible(value);
            }
            if(!value){
                parent.setPreferredSize(new Dimension(parent.getPreferredSize().width, getHeight()));
            }else{
                parent.setPreferredSize(null);
            }
        }
    }

    private final JButton expandButton = new JButton("˄");

    public AccordionTopBar() {
        setLayout(new BorderLayout(5,0));

        //add expand button
        expandButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        add(expandButton, BorderLayout.WEST);
        expandButton.addActionListener((ActionEvent e) -> setExpanded(!expanded));

        //add Label
        add(label, BorderLayout.CENTER);
        label.putClientProperty(FlatClientProperties.STYLE_CLASS, "large");

        putClientProperty("Component.borderWidth", 4);

        var border = new FlatRoundBorder();
        setBorder(border);

        SwingUtilities.invokeLater(() -> {
            setExpanded(expanded);
        });
    }

    public String getTitle() {
        return label.getText();
    }

    public void setTitle(String value) {
        label.setText(value);
    }
}
