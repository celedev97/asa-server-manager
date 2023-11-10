package dev.cele.asa_sm.ui.components

import dev.cele.asa_sm.ui.components.NumberField
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JSlider

class SliderWithText(min: Int = 0, max: Int = 100, value: Int = 0) : JPanel() {
    val slider = JSlider(min, max, value)
    val numberField = NumberField(value)

    init {
        layout = BorderLayout()
        add(slider, BorderLayout.CENTER)
        add(numberField, BorderLayout.EAST)

        slider.addChangeListener {
            if(numberField.value != slider.value){
                numberField.value = slider.value
            }
        }
        numberField.addValueChangedListener {
            if(slider.value != numberField.value){
                slider.value = numberField.value.toInt()
            }
        }
    }

    fun addChangeListener(listener: (Number) -> Unit) {
        slider.addChangeListener { listener(slider.value) }
    }
}
