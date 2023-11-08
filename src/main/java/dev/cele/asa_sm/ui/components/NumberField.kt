package dev.cele.asa_sm.ui.components

import dev.cele.asa_sm.ui.addValueChangedListener
import javax.swing.JTextField
import javax.swing.SwingUtilities

class NumberField(startingValue: Int): JTextField() {

    var value: Int = startingValue
        get() = field
        set(value) {
            field = value
            SwingUtilities.invokeLater {
                text = value.toString()
            }
        }

    var valueChangeListeners: MutableList<(Int) -> Unit> = mutableListOf()
    fun addValueChangedListener(function: (Int) -> Unit) {
        valueChangeListeners.add(function)
    }

    fun removeValueChangedListener(function: (Int) -> Unit) {
        valueChangeListeners.remove(function)
    }

    init {
        this.value = startingValue

        addKeyListener(object : java.awt.event.KeyAdapter() {
            override fun keyReleased(e: java.awt.event.KeyEvent?) {
                super.keyReleased(e)
                try{
                    value = Integer.parseInt(text)
                }catch (e: NumberFormatException){
                    //if the text is not a number set back the old value and return
                    text = value.toString()
                    return
                }
                valueChangeListeners.forEach { it(value) }
            }
        })
    }

}
