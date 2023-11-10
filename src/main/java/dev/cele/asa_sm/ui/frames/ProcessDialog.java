package dev.cele.asa_sm.ui.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProcessDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane textPane;

    public ProcessDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose(e);
            }
        });

        // call onClose() on ESCAPE
        contentPane.registerKeyboardAction(
                e -> onClose(null),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void onClose(WindowEvent e) {
        // add your code here if necessary
        dispose();
    }

    private void onOK() {
        // add your code here
        dispose();
    }


    public static void main(String[] args) {
        ProcessDialog dialog = new ProcessDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
