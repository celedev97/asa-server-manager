package dev.cele.asa_sm.dto.ini.game_user_settings;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class GameUserSettingsINI {
    private ServerSettings ServerSettings;
    private SessionSettings SessionSettings;
    private MessageOfTheDay MessageOfTheDay;

    private HashMap<String, HashMap<String, String>> extraIniMap = new HashMap<>();
}
