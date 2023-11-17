package dev.cele.asa_sm.ui.frames;

import dev.cele.asa_sm.SimpleLogger;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.ProcessOutputDto;
import dev.cele.asa_sm.services.CommandRunnerService;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.function.Consumer;

public class ProcessDialog extends JDialog implements SimpleLogger {

    private final CommandRunnerService commandRunnerService = SpringApplicationContext.autoWire(CommandRunnerService.class);
    private final JTextPane textPane = new JTextPane();
    private final JLabel statusLabel = new JLabel("Idle");
    private final JButton closeButton = new JButton("Close");

    private final String[] commandAndArgs;
    private final Consumer<ProcessOutputDto> callback;

    Style red = textPane.addStyle(null, null);
    {
        StyleConstants.setForeground(red, Color.RED);
    }

    public ProcessDialog(JFrame parent, String title, String[] commandAndArgs) {
        this(parent, title, commandAndArgs, null);
    }

    public ProcessDialog(JFrame parent, String title, String[] commandAndArgs, Consumer<ProcessOutputDto> callback) {
        super(parent, title, true);

        this.commandAndArgs = commandAndArgs;
        this.callback = callback;

        setLayout(new BorderLayout());

        //textpane
        textPane.setEditable(false);
        textPane.setFont(
                UIManager.getFont( "monospaced.font" ).deriveFont( 14f )
        );
        textPane.setBackground(new Color(30,31,34,255));
        DefaultCaret caret = (DefaultCaret)textPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //scroll and nowrap
        JPanel noWrapPanel = new JPanel( new BorderLayout() );
        noWrapPanel.add(textPane);
        JScrollPane scrollPane = new JScrollPane(noWrapPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        //bottom panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(new JLabel("Status:"), BorderLayout.WEST);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        closeButton.setEnabled(false);
        closeButton.addActionListener(e -> {
            JDialog dialog = (JDialog) SwingUtilities.getRoot(ProcessDialog.this);
            dialog.dispose();
        });
        statusPanel.add(closeButton, BorderLayout.SOUTH);
        add(statusPanel, BorderLayout.SOUTH);

        //final setup
        setSize(1115, 630);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    boolean firstTimeVisible = true;
    @Override
    public void setVisible(boolean value){
        if(firstTimeVisible && value){
            firstTimeVisible = false;
            runCommand();
        }
        super.setVisible(value);
    }

    private void runCommand() {
        statusLabel.setText("Running...");
        new Thread(() -> {
            var result = commandRunnerService.runCommand(this, commandAndArgs);
            closeButton.setEnabled(true);
            statusLabel.setText("Finished, code="+result.getExitCode()); // or "Failed!" depending on the result
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            if(callback != null) {
                callback.accept(result);
            }
        }).start();
    }

    @SneakyThrows
    @Override
    public void info(String message) {
        textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), message + "\n", null);
    }

    @SneakyThrows
    @Override
    public void error(String message) {
        textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), message + "\n", red);
    }

}
