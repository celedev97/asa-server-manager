package dev.cele.asa_sm;

import org.apache.commons.lang3.SystemUtils;

import java.nio.file.Path;

public final class Const {
    public final static String ASA_STEAM_GAME_NUMBER = "2430930";

    public final static Path DATA_DIR = Path.of("data");
    public final static Path PROFILES_DIR = DATA_DIR.resolve("profiles");
    public final static Path SERVERS_DIR = Path.of("servers");


    public final static Path THEME_DIR = DATA_DIR.resolve("themes");
    public final static Path MOD_CACHE_DIR = DATA_DIR.resolve("mod_cache");

    public final static Path SETTINGS_FILE = DATA_DIR.resolve("settings.json");

    public final static Path TEMP_DIR = SystemUtils.getJavaIoTmpDir().toPath().resolve("asa_sm");
}
