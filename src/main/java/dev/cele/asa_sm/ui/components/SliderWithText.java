package dev.cele.asa_sm.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderWithText extends JPanel {
    private JSlider slider;
    private NumberField numberField;

    public SliderWithText(){
        this(0, 100, 0);
    }

    public SliderWithText(int min, int max, int value) {
        slider = new JSlider(min, max, value);
        numberField = new NumberField(value);

        setLayout(new BorderLayout());
        add(slider, BorderLayout.CENTER);
        add(numberField, BorderLayout.EAST);

        slider.addChangeListener(e -> {
            if (numberField.getValue().doubleValue() != (double)slider.getValue()) {
                numberField.setValue(slider.getValue());
            }
        });

        numberField.addValueChangedListener(val -> {
            if (slider.getValue() != val.intValue()) {
                slider.setValue(val.intValue());
            }
        });

    }

    public void addChangeListener(Consumer<Number> listener) {
        numberField.addValueChangedListener(listener);
    }
}
