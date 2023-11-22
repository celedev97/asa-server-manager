package dev.cele.asa_sm.ui.components.forms;

import com.formdev.flatlaf.FlatClientProperties;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;
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
            setNumber(number.intValue());
        }
    }

    public void setNumber(Number number){
        if(!Objects.equals(number, this.number)){
            numberListeners.forEach(listener -> listener.accept(number));
            this.number = number;
            updateText();
        }
    }

    private void updateText(){
        setText("" + (isInteger ? number.intValue() : number));
    }

    public NumberField() {
        this(0);
    }

    public NumberField(Number value) {
        super(5);

        //add focus lost listener
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateTextAndUpdateNumber();
            }
        });

        //add listener for ENTER key
        addActionListener(e -> validateTextAndUpdateNumber());
    }

    public void validateTextAndUpdateNumber(){
        var text = getText();
        try {
            number = isInteger ? Integer.parseInt(text) : Double.parseDouble(text);
            numberListeners.forEach(listener -> listener.accept(number));
        } catch (NumberFormatException e) {
            //TODO: make a beep?
            putClientProperty(FlatClientProperties.OUTLINE, "warning");
            if(threadCancelWarning != null){
                threadCancelWarning.interrupt();
            }
            threadCancelWarning = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    return;
                }
                putClientProperty(FlatClientProperties.OUTLINE, null);
            });
            threadCancelWarning.start();
            setText(number.toString());
        }
    }

    Thread threadCancelWarning = null;

    public void addNumberListener(Consumer<Number> listener){
        numberListeners.add(listener);
    }
    public void removeNumberListener(Consumer<Number> listener){
        numberListeners.remove(listener);
    }

}
