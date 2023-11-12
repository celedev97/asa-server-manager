package dev.cele.asa_sm.ui.components.server_tab_accordions;

import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.ui.components.AccordionTopBar;
import dev.cele.asa_sm.ui.components.forms.AutoCompleteField;
import dev.cele.asa_sm.ui.components.forms.NumberField;
import lombok.NoArgsConstructor;

import javax.swing.*;

@NoArgsConstructor
public class AdministrationAccordion {
    private AccordionTopBar accordionTopBar;

    public JPanel contentPane;
    private JPasswordField serverPasswordField;
    private JPasswordField adminPasswordField;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JLabel serverNameLengthLabel;
    private NumberField numberField1;
    private JCheckBox RCONEnabledCheckBox;
    private AutoCompleteField mapNameField;
    private JTextField textField2;
    private JButton button1;

    public AdministrationAccordion(AsaServerConfigDto configDto) {
    }

    void init(){

    }


}
