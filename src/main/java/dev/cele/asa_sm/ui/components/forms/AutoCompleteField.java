package dev.cele.asa_sm.ui.components.forms;

import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import java.util.List;

@NoArgsConstructor
public class AutoCompleteField extends JTextField {

    @Getter
    private boolean strict;
    public void setStrict(boolean value) {
        strict = value;
        if (value) {
            setValue(getValue());
        }
    }

    private List<String> options;

    @Getter
    private String value = "";
    public void setValue(String value) {
        this.value = value;
        if (strict) {
            setText(options.contains(value) ? value : "");
        } else {
            setText(value);
        }
    }

    public AutoCompleteField(boolean strictSetting, List<String> options) {
        this.strict = strictSetting;
        this.options = options;

        addValueChangedListener(text -> {
            if (strict) {
                if (options.contains(text)) {
                    setValue(text);
                }
            } else {
                setValue(text);
            }
        });
    }

    public void addValueChangedListener(java.util.function.Consumer<String> function) {
        getDocument().addDocumentListener(new SimpleDocumentListener(
                text ->{
                    if (strict) {
                        if (options.contains(text)) {
                            function.accept(text);
                        }
                    } else {
                        function.accept(text);
                    }
                }
        ));
    }

}
