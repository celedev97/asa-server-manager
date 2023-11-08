package dev.cele.asa_sm.ui.components

import com.formdev.flatlaf.FlatClientProperties
import java.awt.BorderLayout
import java.awt.Dimension
import java.util.Random
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

open class AccordionPanel(title: String = "Accordion", expanded: Boolean = false) : JPanel() {

    private var accordionTopBar = AccordionTopBar()
    val viewport: JPanel = JPanel()

    var title: String
        get() = accordionTopBar.title
        set(value) {accordionTopBar.title = value}

    var expanded: Boolean = false
        get() = field
        set(value) {
            field = value
            accordionTopBar.expanded = value
            if(value){
                add(viewport, BorderLayout.CENTER)
            }else{
                remove(viewport)
            }
        }


    init {
        background = java.awt.Color(Random().nextInt(255),Random().nextInt(255),Random().nextInt(255),255)
        layout = BorderLayout()

        //add top bar
        add(accordionTopBar, BorderLayout.NORTH)

        this.title = title
        this.expanded = expanded
    }


}

class AccordionTopBar : JPanel() {
    var expanded: Boolean = false
        get() = field
        set(value) {
            field = value
            expandButton.text = if(value) "˄" else "˅"
        }

    var title: String
        get() = label.text
        set(value) {label.text = value}

    private val label = JLabel()
    private val expandButton = JButton()

    init {
        layout = BorderLayout()

        //add expand button
        expandButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT)
        add(expandButton, BorderLayout.WEST)
        expandButton.addActionListener {
            (this.parent as AccordionPanel).apply {
                expanded = !expanded
            }
        }

        //add Label
        add(label, BorderLayout.CENTER)
    }
}
