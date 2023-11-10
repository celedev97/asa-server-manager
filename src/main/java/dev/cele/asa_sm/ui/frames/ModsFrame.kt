package dev.cele.asa_sm.ui.frames

import dev.cele.asa_sm.config.SpringApplicationContext
import dev.cele.asa_sm.dto.AsaServerConfigDto
import dev.cele.asa_sm.dto.curseforge.ModDto
import dev.cele.asa_sm.services.ModCacheService
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.util.Vector
import javax.swing.*
import javax.swing.event.TableModelListener
import javax.swing.table.TableModel
import kotlin.concurrent.thread

class ModsFrame(mainFrame: JFrame, configDto: AsaServerConfigDto): JDialog(mainFrame, "Mods", true) {

    private var loading: Boolean = true
        get() = field
        set(value) {
            field = value
        }


    val modCacheService = SpringApplicationContext.autoWire(ModCacheService::class.java)

    var mods: Vector<ModDto> = Vector()

    init {
        setSize(500, 500)
        setLocationRelativeTo(mainFrame)


        //create a JScrollPane
        val viewPort = JPanel().apply {
            layout = BorderLayout()
        }.also {
            add(JScrollPane(it))
        }

        //create a table model


        thread {
            var modDtos = modCacheService.getMods(configDto.modIds);
            mods.removeAllElements()
            mods.addAll(modDtos)
            loading = false
        }

        setSize(400, 600)
        isResizable = true
        setLocationRelativeTo(null)
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE;
        isVisible = true
    }

}