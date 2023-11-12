package dev.cele.asa_sm.ui.components.forms;

import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NumberField extends JTextField {

    @Getter
    private Number value = 0;

    public void setValue(Number value) {
        this.value = value;
        setText(value.toString());
    }

    public NumberField() {
        this(0);
    }

    public NumberField(Number value) {
        super();

        getDocument().addDocumentListener(new SimpleDocumentListener(text -> {
            try {
                this.value = Double.parseDouble(text);
                valueChangedListeners.forEach(listener -> listener.accept(this.value ));
            } catch (NumberFormatException e) {
                setValue(this.value);
            }
        }));

        setValue(value);
    }

    private final List<Consumer<Number>> valueChangedListeners = new ArrayList<>();
    public void addValueChangedListener(Consumer<Number> function) {
        valueChangedListeners.add(function);
    }
    public void removeValueChangedListener(Consumer<Number> function) {
        valueChangedListeners.remove(function);
    }
}
