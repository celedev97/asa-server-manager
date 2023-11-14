package dev.cele.asa_sm.ui.components.forms;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SliderWithText extends JPanel {
    private final JSlider slider;
    private final NumberField numberField;

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
            if (numberField.getNumber().doubleValue() != (double)slider.getValue()) {
                numberField.setValue(slider.getValue());
            }
        });

        numberField.addNumberListener(val -> {
            if (slider.getValue() != val.intValue()) {
                slider.setValue(val.intValue());
            }
        });

    }

    public void addChangeListener(Consumer<Number> listener) {
        numberField.addNumberListener(listener);
    }
}
