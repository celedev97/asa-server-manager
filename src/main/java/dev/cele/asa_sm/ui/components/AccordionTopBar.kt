package dev.cele.asa_sm.ui.components

import com.formdev.flatlaf.FlatClientProperties
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities

class AccordionTopBar : JPanel() {
    var expanded: Boolean = false
        get() = field
        set(value) {
            field = value
            expandButton.text = if(value) "˄" else "˅"
            this@AccordionTopBar.parent?.apply {
                //check if layout is BorderLayout
                if(layout is BorderLayout){
                    (layout as BorderLayout).getLayoutComponent(BorderLayout.CENTER)?.isVisible = value
                }
            }
        }

    var title: String
        get() = label.text
        set(value) {label.text = value}

    private val label = JLabel()
    private val expandButton = JButton("˄")

    init {
        layout = BorderLayout()

        //add expand button
        expandButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT)
        add(expandButton, BorderLayout.WEST)
        expandButton.addActionListener {
            expanded = !expanded
        }

        //add Label
        add(label, BorderLayout.CENTER)

        SwingUtilities.invokeLater {
            expanded = expanded
        }
    }
}
