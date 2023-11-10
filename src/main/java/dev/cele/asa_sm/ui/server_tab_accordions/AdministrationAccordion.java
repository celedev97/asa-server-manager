package dev.cele.asa_sm.ui.server_tab_accordions;

import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.AccordionTopBar;
import dev.cele.asa_sm.ui.components.NumberField;

import javax.swing.*;

public class AdministrationAccordion {
    public AccordionTopBar accordionTopBar;
    public JPanel contentPane;
    public JPasswordField serverPasswordField;
    public JPasswordField adminPasswordField;
    public JPasswordField passwordField1;
    public JTextField textField1;
    public JLabel serverNameLengthLabel;
    public NumberField numberField1;

    public AdministrationAccordion(AsaServerConfigDto configDto) {
    }


}
