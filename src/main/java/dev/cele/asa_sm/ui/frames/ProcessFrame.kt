package dev.cele.asa_sm.ui.frames

import dev.cele.asa_sm.SimpleLogger
import dev.cele.asa_sm.components.ServerHelperComponent
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.*
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyleContext
import kotlin.concurrent.thread


class ProcessFrame(parent: JFrame, vararg commandAndArgs: String) : JPanel(), SimpleLogger {
    val springHelper = ServerHelperComponent.get();

    val textPane = JTextPane ()

    val statusLabel = JLabel("Running...")
    val closeButton = JButton("Close")

    init {
        //add a text area to display the output, inside a JScrollPane
        layout = BorderLayout()

        textPane.apply {
            isEditable = false
        }

        val noWrapPanel = JPanel(BorderLayout())
        noWrapPanel.add(textPane)

        JScrollPane(noWrapPanel).apply {
            //set vertical scroll always
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            //set horizontal scroll only if necessary
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        }.also {
            add(it, BorderLayout.CENTER)
        }

        JPanel(BorderLayout()).apply {
            add(JLabel("Status:"), BorderLayout.WEST)
            add(statusLabel, BorderLayout.CENTER)
            closeButton.apply {
                isEnabled = false
                addActionListener() {
                    val dialog = SwingUtilities.getRoot(this@ProcessFrame) as JDialog
                    dialog.dispose()
                }
            }.also {
                add(it, BorderLayout.SOUTH)
            }
        }.also {
            add(it, BorderLayout.SOUTH)
        }

        //create a process to execute the command
        thread(start = true) {
            var result = springHelper.commandRunnerService.runCommand(this, *commandAndArgs)
            closeButton.isEnabled = true
            statusLabel.text = if(result.exitCode == 0) "Success!" else "Failed!"
            val dialog = SwingUtilities.getRoot(this@ProcessFrame) as JDialog
            dialog.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE;
        }

    }

    override fun info(message: String?) {
        val sc = StyleContext.getDefaultStyleContext()

        val aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontFamily, "Lucida Console")

        textPane.styledDocument.insertString(textPane.styledDocument.length, message+"\n", aset)
    }


    override fun error(message: String?) {
        val sc = StyleContext.getDefaultStyleContext()

        var aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontFamily, "Lucida Console")
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED)
        aset = sc.addAttribute(aset, StyleConstants.Foreground, Color.RED);

        textPane.styledDocument.insertString(textPane.styledDocument.length, message+"\n", aset)
    }

    companion object {
        fun run(frame: JFrame, downloadVerifyServerCommand: Array<String>) {
            println("Running command: ${downloadVerifyServerCommand.joinToString(" ")}");

            JDialog(frame, "Downloading and verifying server", true).apply {
                add(ProcessFrame(frame, *downloadVerifyServerCommand))
                setSize(400, 600)
                isResizable = true
                setLocationRelativeTo(null)
                isVisible = true
                defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE;
            }
        }
    }

}