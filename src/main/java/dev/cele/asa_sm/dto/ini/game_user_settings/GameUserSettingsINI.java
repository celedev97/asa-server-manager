package dev.cele.asa_sm.dto.ini.game_user_settings;

import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;
import lombok.Getter;
import lombok.Setter;
import org.ini4j.Profile;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class GameUserSettingsINI {
    private ServerSettings ServerSettings = new ServerSettings();
    private SessionSettings SessionSettings = new SessionSettings();
    private MessageOfTheDay MessageOfTheDay = new MessageOfTheDay();

    @IniExtraMap
    private Map<String, Map<String, Object>> extraIniMap = new HashMap<>();
}
