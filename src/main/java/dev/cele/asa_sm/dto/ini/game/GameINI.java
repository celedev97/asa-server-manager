package dev.cele.asa_sm.dto.ini.game;

import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;

import java.util.HashMap;
import java.util.Map;

public class GameINI {
    public ShooterGameMode shooterGameMode;

    @IniExtraMap
    private Map<String, Map<String, Object>> extraIniMap = new HashMap<>();
}
