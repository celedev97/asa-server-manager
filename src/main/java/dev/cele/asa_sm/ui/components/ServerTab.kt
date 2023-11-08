package dev.cele.asa_sm.ui.components

import dev.cele.asa_sm.components.ServerHelperComponent
import dev.cele.asa_sm.dto.AsaServerConfigDto
import dev.cele.asa_sm.ui.addValueChangedListener
import dev.cele.asa_sm.ui.frames.ProcessFrame
import dev.cele.asa_sm.ui.server_tab_accordions.AdministrationAccordion
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Label
import java.io.File
import java.nio.file.Path
import javax.swing.*


class ServerTab(val configDto: AsaServerConfigDto) : JPanel() {

    var log: Logger = LoggerFactory.getLogger(ServerTab::class.java)

    var installedVersionLabel = JLabel("-");
    var outDatedModsLabel = JLabel("-");
    var installationLocationLabel = JLabel("-");
    var serverStatusLabel = JLabel("-");
    var availabilityLabel = JLabel("-");
    var playerCount = JLabel("-/-");

    var scrollPaneContent = JPanel();

    val springHelper: ServerHelperComponent
        get() = ServerHelperComponent.get()

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

        GroupedPanel("").apply {
            layout = GridBagLayout()

            //region row 1: profile id and find button
            val gbcrow1 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                anchor = GridBagConstraints.LINE_START
                gridy = 0
            }

            JLabel("Profile ID:").also {
                add(it, gbcrow1)
            }

            JLabel(configDto.guid).also {
                add(it, (gbcrow1.clone() as GridBagConstraints).apply { gridwidth = 5; weightx = 1.0 })
            }

            //find button
            JButton("Find").apply {
                addActionListener {
                    //find all the labels in the scrollPaneContent and all the childs
                    var labels = findAllLabels(scrollPaneContent)
                }
            }.also {
                add(it, gbcrow1)
            }

            //import/export button
            JButton("Import/Export").apply {
                addActionListener {

                }
            }.also {
                add(it, gbcrow1)
            }
            //endregion

            //region row 2: profile name and save button

            var gbcrow2 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 1
            }

            JLabel("Profile Name:").also {
                add(it, gbcrow2)
            }

            JTextField(configDto.profileName).apply {
                //add event listener for value change
                document.addValueChangedListener {
                    configDto.profileName = it
                    //find the jtabbedpane and change the title
                    val tabbedPane =  SwingUtilities.getAncestorOfClass(
                        JTabbedPane::class.java, this@ServerTab
                    ) as JTabbedPane

                    tabbedPane.setTitleAt(tabbedPane.selectedIndex, it)
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { gridwidth =6; weightx = 1.0 })
            }

            //save button
            JButton("Save").apply {
                addActionListener {
                    saveJson()
                }
            }.also {
                add(it, gbcrow2)
            }

            //endregion

            //region row 3:
            // left side: Installed version label, patch note button
            // center: outdated mods label, update mods button
            // right side: open server log folder button, verify/update server button

            var gbcrow3 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 2
            }

            //left side
            JLabel("Installed Version:").also {
                add(it, gbcrow3)
            }
            installedVersionLabel.also {
                add(it, gbcrow3)
            }

            JButton("Patch Notes").apply {
                addActionListener {

                }
            }.also {
                add(it, gbcrow3)
            }

            //center
            JLabel("Outdated Mods:").also {
                add(it, (gbcrow3.clone() as GridBagConstraints).apply { weightx = 1.0;})
            }
            outDatedModsLabel.also {
                add(it)
            }
            JButton("Update Mods").apply {
                addActionListener {

                }
            }.also {
                add(it, gbcrow3)
            }

            //right side
            JButton("Open Server Log Folder").apply {
                addActionListener {

                }
            }.also {
                add(it, gbcrow3)
            }
            JButton("Verify/Update Server").apply {
                addActionListener {
                    ProcessFrame.run(SwingUtilities.getWindowAncestor(this) as JFrame, springHelper.steamCMDService.downloadVerifyServerCommand("servers"+File.separator+configDto.guid));
                }
            }.also {
                add(it, gbcrow3)
            }

            //endregion


            //region row 4:
            //left side: Installation Location label
            //right side: open installation location button, set installation location button

            var gbcrow4 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 3
            }

            //left side
            JLabel("Installation Location: ").also {
                add(it, gbcrow4)
            }

            JLabel("servers" + File.separator + configDto.guid.toString()).also {
                add(it, (gbcrow4.clone() as GridBagConstraints).apply { gridwidth=6; weightx = 1.0 })
            }

            //right side
            JButton("Open Installation Location").apply {
                addActionListener {

                }
            }.also {
                add(it, gbcrow4)
            }

            //endregion

            //region row 5:
            //left side: server status label (double label)

            var gbcrow5 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 4
            }

            JLabel("Server Status:").also {
                add(it, gbcrow5)
            }
            serverStatusLabel.also {
                add(it, gbcrow5)
            }

            //center: availability label (double label)
            JLabel("Availability:").also {
                add(it, (gbcrow5.clone() as GridBagConstraints).apply { weightx = 1.0; })
            }
            availabilityLabel.also {
                add(it, gbcrow5)
            }

            //right side: player count label (double label), start button, rcon button
            JLabel("Player Count:").also {
                add(it, (gbcrow5.clone() as GridBagConstraints).apply { weightx = 1.0; })
            }
            playerCount.also {
                add(it, gbcrow5)
            }

            JButton("Start").apply {
                addActionListener {
                    springHelper.commandRunnerService.runCommand(*configDto.command)
                }
            }.also {
                add(it, gbcrow5)
            }
            JButton("RCON").apply {
                addActionListener {

                }
            }.also {
                add(it, gbcrow5)
            }


        } .also { scrollPaneContent.add(it, globalVerticalGBC)  }


        //create a group named "Administration"
        var administrationAccordion = AdministrationAccordion(configDto).apply {
            expanded = true
        }.also {
            scrollPaneContent.add(it, globalVerticalGBC)
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
        Path.of("data"+File.separator+"profiles"+File.separator+configDto.guid.toString()+".json")
            .toFile()
            .writeText(
                springHelper.objectMapper.writeValueAsString(configDto)
            )
        log.info("Saved configDto to"+File.separator+configDto.guid.toString()+".json file")
    }

}
