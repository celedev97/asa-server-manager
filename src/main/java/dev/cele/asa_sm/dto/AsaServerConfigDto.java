package dev.cele.asa_sm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.cele.asa_sm.enums.MapsEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
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

    private String map = MapsEnum.THE_ISLAND.getMapName();
    private String guid = UUID.randomUUID().toString();
    private String profileName = "Server";
    private String serverName = "";
    private String serverPassword = "";
    private String serverAdminPassword = "";
    private int serverPort = 7777;
    private int serverQueryPort = 27015;
    private int serverMaxPlayers = 70;
    private boolean battlEye = false;
    private String serverSpectatorPassword = "";
    private String serverLocalIp = "";
    private boolean rconEnabled = false;
    private int rconPort = 32330;
    private int rconServerLogBuffer = 600;
    private String modIds = "";
    private int autoSavePeriod = 15;

    @JsonIgnore
    public String[] getCommand() {
        List<String> mainArgs = new ArrayList<>();

        mainArgs.add(map);
        mainArgs.add("listen");
        if (!serverName.isEmpty()) mainArgs.add("SessionName=" + serverName);
        if (!serverPassword.isEmpty()) mainArgs.add("ServerPassword=" + serverPassword);
        if (!serverAdminPassword.isEmpty()) mainArgs.add("ServerAdminPassword=" + serverAdminPassword);
        if (serverPort != 0) mainArgs.add("Port=" + serverPort);
        if (serverQueryPort != 0) mainArgs.add("QueryPort=" + serverQueryPort);
        if (serverMaxPlayers != 0) mainArgs.add("MaxPlayers=" + serverMaxPlayers);

        List<String> extraArgs = new ArrayList<>();
        if (!battlEye) {
            extraArgs.add("-NoBattlEye");
        }

        List<String> finalCommand = new ArrayList<>();
        finalCommand.add("servers" + File.separator + guid + File.separator + "ShooterGame" + File.separator + "Binaries" + File.separator + "Win64" + File.separator + "ArkAscendedServer.exe");
        finalCommand.add(String.join("?", mainArgs));
        finalCommand.addAll(extraArgs);

        return finalCommand.toArray(new String[0]);
    }
}
