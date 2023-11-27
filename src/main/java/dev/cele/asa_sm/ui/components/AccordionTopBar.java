package dev.cele.asa_sm.ui.components;

import com.formdev.flatlaf.FlatClientProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Slf4j
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
                parent.setPreferredSize(new Dimension(parent.getPreferredSize().width, getHeight()+4));
            }else{
                parent.setPreferredSize(null);
            }
        }else{
            log.error("Accordion("+label.getText() + ") has no parent or parent layout is not BorderLayout");
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

        new Thread(() -> {
            var border = BorderFactory.createStrokeBorder(new BasicStroke(1f), UIManager.getColor("Component.borderColor"));
            JPanel parent;
            while((parent = (JPanel)getParent()) == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            parent.setBorder(border);
        }).start();

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
