package dev.cele.asa_sm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.dto.ini.game_user_settings.GameUserSettingsINI;
import dev.cele.asa_sm.enums.MapsEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
@FieldNameConstants
public class AsaServerConfigDto {

    //region stuff for the unsaved variable
    @JsonIgnore
    private boolean unsaved = false;
    public void setUnsaved(boolean value){
        unsaved = value;
        unsavedChangeListeners.forEach(listener -> listener.accept(value));
    }

    @JsonIgnore
    private List<Consumer<Boolean>> unsavedChangeListeners = new ArrayList<>();
    @JsonIgnore
    public void addUnsavedChangeListener(Consumer<Boolean> listener){
        unsavedChangeListeners.add(listener);
    }
    @JsonIgnore
    public void removeUnsavedChangeListener(Consumer<Boolean> listener){
        unsavedChangeListeners.remove(listener);
    }
    //endregion

    //region Administration
    private String map = MapsEnum.THE_ISLAND.getMapName();
    private String guid = UUID.randomUUID().toString();

    private String profileName = "Server";
    private String serverName = "";

    private String serverPassword = "";
    private String serverAdminPassword = "";
    private String serverSpectatorPassword = "";

    private int serverPort = 7777;
    private int serverQueryPort = 27015;
    private int serverMaxPlayers = 70;

    private boolean rconEnabled = false;
    private int rconPort = 32330;
    private int rconServerLogBuffer = 600;

    private String modIds = "";
    //endregion


    GameUserSettingsINI gameUserSettingsINI = new GameUserSettingsINI();



    private boolean battlEye = false;

    private int autoSavePeriod = 15;

    @JsonIgnore
    public String[] getCommand() {
        List<String> mainArgs = new ArrayList<>();

        mainArgs.add(map);
        mainArgs.add("listen");

        //name and password
        if (!serverName.isEmpty()) mainArgs.add("SessionName=" + serverName);

        if (!serverPassword.isEmpty()) mainArgs.add("ServerPassword=" + serverPassword);
        if (!serverAdminPassword.isEmpty()) mainArgs.add("ServerAdminPassword=" + serverAdminPassword);
        if (!serverSpectatorPassword.isEmpty()) mainArgs.add("SpectatorPassword=" + serverSpectatorPassword);

        //networking
        if (serverPort != 0) mainArgs.add("Port=" + serverPort);
        if (serverQueryPort != 0) mainArgs.add("QueryPort=" + serverQueryPort);
        if (serverMaxPlayers != 0) mainArgs.add("MaxPlayers=" + serverMaxPlayers);

        //TODO: move RCON config to the ini file?
        if (rconEnabled) {
            mainArgs.add("RCONEnabled=True");
            mainArgs.add("RCONPort=" + rconPort);
            mainArgs.add("RCONServerGameLogBuffer=" + rconServerLogBuffer);
        }


        //extra separate args
        List<String> extraArgs = new ArrayList<>();
        if (!battlEye) {
            extraArgs.add("-NoBattlEye");
        }

        if(!modIds.isEmpty()){
            extraArgs.add("-mods=" + modIds);
        }

        List<String> finalCommand = new ArrayList<>();
        finalCommand.add(
                Const.SERVERS_DIR
                        .resolve(guid)
                        .resolve("ShooterGame")
                        .resolve("Binaries")
                        .resolve("Win64")
                        .resolve("ArkAscendedServer.exe")
                        .toAbsolutePath().toString()
        );
        finalCommand.add(String.join("?", mainArgs));
        finalCommand.addAll(extraArgs);

        return finalCommand.toArray(new String[0]);
    }
}
