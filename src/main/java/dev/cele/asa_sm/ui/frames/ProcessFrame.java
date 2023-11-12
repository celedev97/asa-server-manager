package dev.cele.asa_sm.ui.frames;

import dev.cele.asa_sm.SimpleLogger;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.services.CommandRunnerService;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProcessFrame extends JDialog implements SimpleLogger {

    private final CommandRunnerService commandRunnerService = SpringApplicationContext.autoWire(CommandRunnerService.class);
    private final JTextPane textPane = new JTextPane();
    private final JLabel statusLabel = new JLabel("Running...");
    private final JButton closeButton = new JButton("Close");

    public ProcessFrame(JFrame parent, String... commandAndArgs) {
        super(parent);
        setLayout(new BorderLayout());

        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(new JLabel("Status:"), BorderLayout.WEST);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        closeButton.setEnabled(false);
        closeButton.addActionListener(e -> {
            JDialog dialog = (JDialog) SwingUtilities.getRoot(ProcessFrame.this);
            dialog.dispose();
        });
        statusPanel.add(closeButton, BorderLayout.SOUTH);
        add(statusPanel, BorderLayout.SOUTH);

        Executors.newSingleThreadExecutor().submit(() -> {
            ProcessFrame.this.runCommand(commandAndArgs);
            closeButton.setEnabled(true);
            statusLabel.setText("Success!"); // or "Failed!" depending on the result
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });

        setSize(400, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    private void runCommand(String... commandAndArgs) {
        commandRunnerService.runCommand(this, commandAndArgs);
    }

    @SneakyThrows
    @Override
    public void info(String message) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        var aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontFamily, "Lucida Console");
        textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), message + "\n", aset);
    }

    @SneakyThrows
    @Override
    public void error(String message) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        var aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        aset = sc.addAttribute(aset, StyleConstants.Foreground, Color.RED);

        textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), message + "\n", aset);
    }

    public static void run(JFrame frame, String[] downloadVerifyServerCommand) {
        System.out.println("Running command: " + String.join(" ", downloadVerifyServerCommand));
        SwingUtilities.invokeLater(() -> new ProcessFrame(frame, downloadVerifyServerCommand).setVisible(true));
    }
}
