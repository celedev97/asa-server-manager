package dev.cele.asa_sm.ui.components.forms;

import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NumberField extends JTextField {

    private final ArrayList<Consumer<Number>> numberListeners = new ArrayList<>();

    @Getter
    private Number number = 0;

    @Getter
    private boolean isInteger = true;
    public void setIsInteger(boolean isInteger){
        this.isInteger = isInteger;
        if(isInteger){
            this.number = number.intValue();
        }
        validateTextAndUpdate(getText());
    }

    public void setNumber(Number number){
        this.number = number;
        setText(number.toString());
    }

    public NumberField() {
        this(0);
    }

    public NumberField(Number value) {
        super(5);

        //add focus lost listener
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateTextAndUpdate(getText());
            }
        });
    }

    private void validateTextAndUpdate(String text){
        try {
            number = isInteger ? Integer.parseInt(text) : Double.parseDouble(text);
            numberListeners.forEach(listener -> listener.accept(number));
        } catch (NumberFormatException e) {
            //TODO: make a beep?
            setText(number.toString());
        }
    }

    public void addNumberListener(Consumer<Number> listener){
        numberListeners.add(listener);
    }
    public void removeNumberListener(Consumer<Number> listener){
        numberListeners.remove(listener);
    }

}
