package dev.cele.asa_sm.dto.ini.game_user_settings;

import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;
import dev.cele.asa_sm.dto.ini.IniValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@IniSection("MessageOfTheDay")
@Getter @Setter
public class MessageOfTheDay {
    @IniValue("Duration")
    private Integer duration = 20;

    @IniValue("Message")
    private String message = null;

    @IniExtraMap
    Map<String, String> extraIniMap;
}
