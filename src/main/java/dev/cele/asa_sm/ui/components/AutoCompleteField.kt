package dev.cele.asa_sm.ui.components

import dev.cele.asa_sm.ui.listeners.SimpleDocumentListener
import javax.swing.JTextField

class AutoCompleteField(strictSetting: Boolean, var options: List<String> = mutableListOf()): JTextField() {

    fun addValueChangedListener(function: (String) -> Unit) {
        super.getDocument().addDocumentListener(SimpleDocumentListener{
            if(strict){
                if(options.contains(text)){
                    function(text)
                }
            }else{
                function(text)
            }
        })
    }

    var strict: Boolean = false
        get() = field
        set(value) {
            field = value
            if(value){
                this.value = this.value
            }
        }

    var value = ""
        get() = field
        set(value) {
            field = value
            if(strict){
                text = if(options.contains(value)) value else ""
            }else{
                text = value
            }
        }

    init {
        this.strict = strictSetting

    }


}
