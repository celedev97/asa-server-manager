package dev.cele.asa_sm.ui.components

import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class GroupedPanel(title: String): JPanel(GridBagLayout()) {

    init {
        border = BorderFactory.createTitledBorder(title.ifEmpty {" "})
    }

}
