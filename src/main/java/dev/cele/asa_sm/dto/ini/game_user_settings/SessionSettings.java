package dev.cele.asa_sm.dto.ini.game_user_settings;

import dev.cele.asa_sm.dto.ini.IniSection;
import dev.cele.asa_sm.dto.ini.IniValue;
import lombok.Getter;
import lombok.Setter;

@IniSection(name = "SessionSettings")
@Getter @Setter
public class SessionSettings {
    @IniValue("MultiHome")
    private String multiHome = null;
    @IniValue("Port")
    private Integer port = 7777;
    @IniValue("QueryPort")
    private Integer queryPort = 27015;
    @IniValue("SessionName")
    private String sessionName = "ARK #123456";

}