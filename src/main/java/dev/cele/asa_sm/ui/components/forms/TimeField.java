package dev.cele.asa_sm.ui.components.forms;

import javax.swing.*;
import java.time.LocalTime;

public class TimeField extends JTextField {

    private LocalTime time;

    public TimeField() {
        super(5);
        setText("00:00");



    }
}
