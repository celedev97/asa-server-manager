package dev.cele.asa_sm.ui.frames

import com.fasterxml.jackson.databind.ObjectMapper
import dev.cele.asa_sm.Const.PROFILES_DIR
import dev.cele.asa_sm.dto.AsaServerConfigDto
import dev.cele.asa_sm.ui.components.ServerTab
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.awt.BorderLayout
import java.awt.Dimension
import java.nio.file.Files
import java.util.Objects.nonNull
import javax.swing.JFrame
import javax.swing.JTabbedPane
import javax.swing.SwingUtilities

@Component
@Slf4j
class MainFrame(
    private val objectMapper: ObjectMapper
) : JFrame() {
    val tabbedPane = JTabbedPane()


    init {
        title = "ASA SM"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(800, 600)
        setLocationRelativeTo(null)
        isVisible = true
        layout = BorderLayout()

        Files.createDirectories(PROFILES_DIR);

        //look for json files in the profiles directory
        val profiles = Files.list(PROFILES_DIR)
            .filter { it.toString().endsWith(".json") }
            .toList()
            .map {
                try{
                    return@map objectMapper.readValue(it.toFile(), AsaServerConfigDto::class.java)
                }catch (e: Exception){
                    println("Error reading profile file: ${it.fileName}")
                }
                return@map null
            }
            .filterNotNull()
            .toMutableList()

        if(profiles.isEmpty()){
            //if there are no profiles create a new one
            addProfile()
        }else{
            profiles.forEach {
                addProfile(it)
            }
        }

        tabbedPane.also {
            add(it, BorderLayout.CENTER)
        }


        //add a plus button to add a new profile, the button should be on the right side of the tabbed pane

        tabbedPane.addTab("+", null);
        tabbedPane.addChangeListener {
            if(tabbedPane.selectedIndex == tabbedPane.tabCount-1){
                SwingUtilities.invokeLater {
                    tabbedPane.selectedIndex = 0
                    addProfile()
                    tabbedPane.selectedIndex = tabbedPane.tabCount-2
                }
            }
        }

        pack()

    }

    fun addProfile(){
        addProfile(null)
    }
    fun addProfile(input: AsaServerConfigDto?){
        val profile = if(nonNull(input)) input!! else AsaServerConfigDto().apply { serverName = "Default" }
        val index = if(tabbedPane.tabCount>0) (tabbedPane.tabCount - 1) else 0
        tabbedPane.insertTab(profile.serverName, null, ServerTab(profile), null, index)
    }

}
