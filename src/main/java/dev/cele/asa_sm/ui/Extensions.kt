package dev.cele.asa_sm.ui

import javax.swing.event.DocumentListener
import javax.swing.text.Document


public fun Document.addValueChangedListener(function: (String) -> Unit) {
    val textBox = this
    addDocumentListener(object : DocumentListener {
        override fun insertUpdate(e: javax.swing.event.DocumentEvent?) {
            function(textBox.getText(0, textBox.length))
        }

        override fun removeUpdate(e: javax.swing.event.DocumentEvent?) {
            function(textBox.getText(0, textBox.length))
        }

        override fun changedUpdate(e: javax.swing.event.DocumentEvent?) {
            function(textBox.getText(0, textBox.length))
        }
    })
}