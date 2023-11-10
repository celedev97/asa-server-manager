package dev.cele.asa_sm.ui

import com.fasterxml.jackson.databind.ObjectMapper
import dev.cele.asa_sm.config.SpringApplicationContext
import dev.cele.asa_sm.dto.AsaServerConfigDto
import dev.cele.asa_sm.services.CommandRunnerService
import dev.cele.asa_sm.services.SteamCMDService
import dev.cele.asa_sm.ui.server_tab_accordions.AdministrationAccordion
import dev.cele.asa_sm.ui.server_tab_accordions.TopPanel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Label
import java.io.File
import java.nio.file.Path
import javax.swing.*
import kotlin.concurrent.thread


class ServerTab(val configDto: AsaServerConfigDto) : JPanel() {

    private var topPanel: TopPanel

    val steamCMDService = SpringApplicationContext.autoWire(SteamCMDService::class.java)
    val commandRunnerService = SpringApplicationContext.autoWire(CommandRunnerService::class.java)
    val objectMapper = SpringApplicationContext.autoWire(ObjectMapper::class.java)

    var log: Logger = LoggerFactory.getLogger(ServerTab::class.java)

    var scrollPaneContent = JPanel();

    var serverThread: Thread? = null

    init {
        //region initial setup, var scrollPaneContent = JPanel()
        layout = BorderLayout()

        val globalVerticalGBC = GridBagConstraints().apply {
            fill = GridBagConstraints.HORIZONTAL
            anchor = GridBagConstraints.BASELINE
            gridwidth = GridBagConstraints.REMAINDER
            weightx = 1.0
        }

        //create a JScrollPane and a content panel
        scrollPaneContent.apply {
            layout = GridBagLayout()
        }
        JScrollPane().apply {
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            viewport.add(scrollPaneContent)
        }.also {
            add(it, BorderLayout.CENTER)
        }
        //endregion

        //top panel
        topPanel = TopPanel(configDto).also {
            scrollPaneContent.add(it.contentPane, globalVerticalGBC)
        }


        //create a group named "Administration"
        AdministrationAccordion(configDto).apply {

        }.also {
            scrollPaneContent.add(it.contentPane, globalVerticalGBC)
        }

        //create a group named "Automatic Management"

        //create a group named "Server Details"

        //create a group named "Rules"

        //create a group named "Chat and Notifications"

        //create a group named "HUD and Visuals"

        //create a group named "Player Settings"

        //create a group named "Dino Settings"

        //create a group named "Environment"

        //create a group named "Structures"

        //create a group named "Engrams"

        //create a group named "Server File Details"

        //create a group named "Custom GameUserSettings.ini Settings"

        //create a group named "Custom Game.ini Settings"

        //create a group named "Player and Dino Level Progressions"

        //create a group named "Crafting Overrides"

        //create a group named "Stack Size Overrides"

        //create a group named "Map Spawner Overrides"

        //create a group named "Supply Crate Overrides"

        //create a group named "Exclude ItemIDs From Supply Crates Overrides"

        //create a group named "Prevent Transfer Overrides"


        //create an empty filler panel
        JPanel().apply {
            preferredSize = java.awt.Dimension(0, 0)
        }.also {
            scrollPaneContent.add(it, (globalVerticalGBC.clone() as GridBagConstraints).apply { weighty = 1.0 })
        }

    }

    private fun findAllLabels(pane: JPanel, labels: MutableList<Label> = mutableListOf()): MutableList<Label> {
        for (component in pane.components) {
            if (component is Label) {
                labels.add(component)
            } else if (component is JPanel) {
                findAllLabels(component, labels)
            }
        }
        return labels
    }

    private fun saveJson() {
        //save the configDto to a json file
        Path.of("data"+File.separator+"profiles"+File.separator+ configDto.guid +".json")
            .toFile()
            .writeText(
                objectMapper.writeValueAsString(configDto)
            )
        log.info("Saved configDto to"+File.separator+ configDto.guid +".json file")
    }

    fun startServer() {
        //check if the server is running
        if (serverThread != null && serverThread!!.isAlive) {
            serverThread!!.interrupt()
            topPanel.startButton.text = "Start"
            return
        }

        //start the server
        serverThread = thread(start = true){
            commandRunnerService.runCommand(*configDto.getCommand)
            topPanel.startButton.text = "Start"
        }
        topPanel.startButton.text = "Stop"
    }

}
