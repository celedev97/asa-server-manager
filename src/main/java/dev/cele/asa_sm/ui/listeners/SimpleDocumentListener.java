package dev.cele.asa_sm.ui.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.util.function.Consumer;

public class SimpleDocumentListener implements DocumentListener {

    private Consumer<String> action;

    public SimpleDocumentListener(Consumer<String> action) {
        this.action = action;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            action.accept(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            action.accept(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            action.accept(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
