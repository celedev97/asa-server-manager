package dev.cele.asa_sm.ui.server_tab_accordions

import com.sun.java.accessibility.util.SwingEventMonitor.addChangeListener
import dev.cele.asa_sm.components.SliderWithText
import dev.cele.asa_sm.dto.AsaServerConfigDto
import dev.cele.asa_sm.enums.MapsEnum
import dev.cele.asa_sm.ui.addValueChangedListener
import dev.cele.asa_sm.ui.components.AccordionPanel
import dev.cele.asa_sm.ui.components.AutoCompleteField
import dev.cele.asa_sm.ui.components.GroupedPanel
import dev.cele.asa_sm.ui.components.NumberField
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import javax.swing.*

class AdministrationAccordion(private val configDto: AsaServerConfigDto): AccordionPanel("Administration"){
    init {
        viewport.layout = BoxLayout(viewport, BoxLayout.Y_AXIS)

        //add a named group called "Name and Passwords"
        GroupedPanel("Name and Passwords").apply {
            val nameLen = JLabel("0")

            //region Row 1: server name
            var gbcrow1 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                anchor = GridBagConstraints.LINE_START
                gridy = 0
            }

            JLabel("Server Name:").also {
                add(it, gbcrow1)
            }

            JTextField(configDto.serverName).apply {
                //add event listener for value change
                document.addValueChangedListener {
                    configDto.serverName = it
                    nameLen.text = it.length.toString()
                }
            }.also {
                add(it, (gbcrow1.clone() as GridBagConstraints).apply {
                    gridwidth = 3
                    weightx = 1.0
                })
            }

            JPanel().apply {
                layout = FlowLayout(FlowLayout.LEFT)
                JLabel("Length: ").also {
                    add(it)
                }
                nameLen.also {
                    add(it)
                }
            }.also {
                add(it, (gbcrow1.clone() as GridBagConstraints).apply {
                    gridwidth = 2
                })
            }


            //endregion

            //row 2, server password, admin password and spectator password, all with label and then text field
            val gbcrow2 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 1
            }

            JLabel("Server Password:").also {
                add(it, gbcrow2)
            }

            JTextField(configDto.serverPassword).apply {
                //add event listener for value change
                document.addValueChangedListener {
                    configDto.serverPassword = it
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }

            JLabel("Admin Password:").also {
                add(it, gbcrow2)
            }

            JTextField(configDto.serverAdminPassword).apply {
                //add event listener for value change
                document.addValueChangedListener {
                    configDto.serverAdminPassword = it
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }

            JLabel("Spectator Password:").also {
                add(it, gbcrow2)
            }

            JTextField(configDto.serverSpectatorPassword).apply {
                //add event listener for value change
                document.addValueChangedListener {
                    configDto.serverSpectatorPassword = it
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }

        }.also {
            viewport.add(it)
        }

        //add a named group called "Networking"
        GroupedPanel("Networking").apply {
            //row 1, local ip label and combobox (which will be populated with network interfaces)
            val gbcrow1 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                anchor = GridBagConstraints.LINE_START
                gridy = 0
            }

            JLabel("Local IP:").also {
                add(it, gbcrow1)
            }

            JComboBox<String>().apply {
                //add event listener for value change
                addActionListener {
                    configDto.serverLocalIp = selectedItem.toString()
                }

            }.also {
                add(it, (gbcrow1.clone() as GridBagConstraints).apply { weightx = 1.0; gridwidth = 3 })
            }

            JButton("↻").apply {
                //add event listener for value change
                addActionListener {

                }
            }.also {
                add(it, gbcrow1)
            }

            //row 2, server port, peer port and query port, all with label and then text field, the peer port should be disabled and set by the server port (it's serverport+1)
            val gbcrow2 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 1
            }

            val peerPort = NumberField(configDto.serverPort + 1)

            JLabel("Server Port:").also {
                add(it, gbcrow2)
            }
            NumberField(configDto.serverPort).apply {
                //add event listener for value change
                addValueChangedListener {
                    configDto.serverPort = it
                    peerPort.value = it + 1
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }

            JLabel("Peer Port:").also {
                add(it, gbcrow2)
            }
            add(peerPort, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })

            JLabel("Query Port:").also {
                add(it, gbcrow2)
            }
            NumberField(configDto.serverQueryPort).apply {
                //add event listener for value change
                addValueChangedListener {
                    configDto.serverQueryPort = it
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }



        }.also {
            viewport.add(it)
        }

        //add a named group called "RCON"
        GroupedPanel("RCON").apply {
            //first row, only RCON enabled checkbox
            val gbcrow1 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                anchor = GridBagConstraints.LINE_START
                gridy = 0
            }

            JCheckBox("RCON Enabled").apply {
                //add event listener for value change
                addActionListener {
                    configDto.rconEnabled = isSelected
                }
            }.also {
                add(it, gbcrow1)
            }

            //second row, RCON port and RCON server log buffer, both with label and then numeric field
            val gbcrow2 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 1
            }

            JLabel("RCON Port:").also {
                add(it, gbcrow2)
            }
            NumberField(configDto.rconPort).apply {
                //add event listener for value change
                addValueChangedListener {
                    configDto.rconPort = it
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }

            JLabel("RCON Server Log Buffer:").also {
                add(it, gbcrow2)
            }
            NumberField(configDto.rconServerLogBuffer).apply {
                //add event listener for value change
                addValueChangedListener {
                    configDto.rconServerLogBuffer = it
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }
        }.also {
            viewport.add(it)
        }

        //add a named group called "Maps and Mods"
        GroupedPanel("Maps and Mods").apply {
            //first row, label "Map Name or Map Mod Path:" and autocomplete combobox
            val gbcrow1 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                anchor = GridBagConstraints.LINE_START
                gridy = 0
            }

            JLabel("Map Name or Map Mod Path:").also {
                add(it, gbcrow1)
            }

            AutoCompleteField(false, MapsEnum.entries.map { it.mapName!! }.toMutableList()).apply {

            }.also {
                add(it, (gbcrow1.clone() as GridBagConstraints).apply { weightx = 1.0; gridwidth = 3 })
            }

            //second row Mod IDs, label "Mod IDs:" and text field, with two small buttons, one to watch mods and one to download, first one with the lens icon as a character, second one with the download icon as a character
            val gbcrow2 = (gbcrow1.clone() as GridBagConstraints).apply {
                gridy = 1
            }

            JLabel("Mod IDs:").also {
                add(it, gbcrow2)
            }
            JTextField().apply {
                //add event listener for value change
                document.addValueChangedListener {
                    configDto.modIds = it
                }
            }.also {
                add(it, (gbcrow2.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }

            JButton("🔍").apply {
                //add event listener for value change
                addActionListener {

                }
            }.also {
                add(it, gbcrow2)
            }

            JButton("\uD83D\uDCE5").apply {
                //add event listener for value change
                addActionListener {

                }
            }.also {
                add(it, gbcrow2)
            }

        }.also {
            viewport.add(it)
        }

        GroupedPanel("Saves").apply {
            // fiorst row, label "Auto Save Period:" and slider with weightx 1.0, then a label with "minutes", a backup now button and a restore button
            val gbcrow1 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                anchor = GridBagConstraints.LINE_START
                gridy = 0
            }

            JLabel("Auto Save Period:").also {
                add(it, gbcrow1)
            }

            SliderWithText(1, 720, configDto.autoSavePeriod).apply {
                //add event listener for value change
                addChangeListener {
                    configDto.autoSavePeriod = it
                }
            }.also {
                add(it, (gbcrow1.clone() as GridBagConstraints).apply { weightx = 1.0 })
            }

            JLabel("minutes").also {
                add(it, gbcrow1)
            }

            JButton("Backup Now").apply {
                //add event listener for value change
                addActionListener {

                }
            }.also {
                add(it, gbcrow1)
            }

            JButton("Restore").apply {
                //add event listener for value change
                addActionListener {

                }
            }.also {
                add(it, gbcrow1)
            }
        }.also {
            viewport.add(it)
        }

    }
}
