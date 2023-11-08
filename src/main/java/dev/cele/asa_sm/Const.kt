package dev.cele.asa_sm

import java.io.File
import java.nio.file.Path

object Const {
    const val ASA_STEAM_GAME_NUMBER = "2430930"

    val PROFILES_DIR = Path.of("data" + File.separator + "profiles")
    val SERVERS_DIR = Path.of("servers")

}
