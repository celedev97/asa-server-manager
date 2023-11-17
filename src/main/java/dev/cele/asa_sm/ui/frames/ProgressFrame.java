package dev.cele.asa_sm.ui.frames;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ProgressFrame extends JDialog {

    private final JLabel label = new JLabel();

    @Getter
    private final JProgressBar progressBar = new JProgressBar();

    public ProgressFrame(Frame owner, String title, String message, boolean indeterminate) {
        super(owner, title, true);
        setLocationRelativeTo(owner);

        label.setText(message == null ? "" : message);

        setLayout(new BorderLayout());

        add(label, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        if(!indeterminate) progressBar.setStringPainted(true);
        progressBar.setIndeterminate(indeterminate);

        pack();
        setSize(new Dimension(getWidth()+ 50, getHeight() + 20));
        setResizable(false);
    }

    public void setProgress(int progress, String message) {
        SwingUtilities.invokeLater(() -> {
            setProgress(progress);
            label.setText(message);
        });
    }

    public void setProgress(int progress) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(progress);
        });
    }

    public void setMessage(String message) {
        label.setText(message);
    }

    public void launch(Consumer<ProgressFrame> action) {
        new Thread(() -> {
            action.accept(this);
            setVisible(false);
        }).start();
        setVisible(true);
    }
}
