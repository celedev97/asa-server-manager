package dev.cele.asa_sm.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import dev.cele.asa_sm.enums.MapsEnum
import lombok.Data
import java.io.File
import java.util.*

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class AsaServerConfigDto {
    var installFolder = ""
    var map = MapsEnum.THE_ISLAND.mapName
    var guid = UUID.randomUUID().toString()
    var profileName = "Server"
    var serverName = ""
    var serverPassword = ""
    var serverAdminPassword = ""
    var serverPort: Int? = 7777
    var serverQueryPort: Int? = 27015
    var serverMaxPlayers: Int? = 70
    var battlEye = true
    var serverSpectatorPassword = ""
    var serverLocalIp = ""
    var rconEnabled = false
    var rconPort = 32330
    var rconServerLogBuffer = 600
    var modIds = ""
    var autoSavePeriod = 15

    @get:JsonIgnore
    val getCommand: Array<String>
        get() {
            //example args TheIsland_WP?listen?SessionName=<SERVER_NAME>?ServerAdminPassword=<ADMIN_PASS>?Port=7777?QueryPort=27015?MaxPlayers=32 -NoBattlEye
            val mainArgs = ArrayList<String>()

            //region composing main arg, separated by ?, and composed by "key=value" or "key"
            mainArgs.add(map)
            mainArgs.add("listen")
            if (serverName.isNotEmpty()) mainArgs.add("SessionName=$serverName")
            if (serverPassword.isNotEmpty()) mainArgs.add("ServerPassword=$serverPassword")
            if (serverAdminPassword.isNotEmpty()) mainArgs.add("ServerAdminPassword=$serverAdminPassword")
            if (serverPort != null) mainArgs.add("Port=$serverPort")
            if (serverQueryPort != null) mainArgs.add("QueryPort=$serverQueryPort")
            if (serverMaxPlayers != null) mainArgs.add("MaxPlayers=$serverMaxPlayers")
            val extraArgs = ArrayList<String>()
            if (!battlEye) {
                extraArgs.add("-NoBattlEye")
            }
            //endregion

            val finalCommand = ArrayList<String>()
            finalCommand.add("servers" + File.separator + guid + File.separator + "ShooterGame" + File.separator + "Binaries" + File.separator + "Win64" + File.separator + "ArkAscendedServer.exe")
            finalCommand.add(java.lang.String.join("?", mainArgs))
            finalCommand.addAll(extraArgs)
            return finalCommand.toTypedArray<String>()
        }
}
