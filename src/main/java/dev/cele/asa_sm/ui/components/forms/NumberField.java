package dev.cele.asa_sm.ui.components.forms;

import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NumberField extends JSpinner {

    private final ArrayList<Consumer<Number>> numberListeners = new ArrayList<>();

    public Number getNumber() {
        return (Number) getValue();
    }

    public NumberField() {
        this(0);
    }

    public NumberField(Number value) {
        super();
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this, "#");
        setEditor(editor);

        setValue(value);

        addChangeListener(e -> {
            numberListeners.forEach(l -> l.accept((Number) getValue()));
        });
    }

    public void addNumberListener(Consumer<Number> listener){
        numberListeners.add(listener);
    }
    public void removeNumberListener(Consumer<Number> listener){
        numberListeners.remove(listener);
    }

}
