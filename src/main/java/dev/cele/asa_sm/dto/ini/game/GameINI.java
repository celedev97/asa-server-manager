package dev.cele.asa_sm.dto.ini.game;

import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class GameINI {
    private ShooterGameMode shooterGameMode;

    @IniExtraMap
    private Map<String, Map<String, Object>> extraIniMap = new HashMap<>();
}
