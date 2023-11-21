package dev.cele.asa_sm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.dto.ini.game_user_settings.GameUserSettingsINI;
import dev.cele.asa_sm.enums.MapsEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldNameConstants
@Slf4j
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

    @ExtraCommandLineArgument("mods")
    private String modIds = "";

    @ExtraCommandLineArgument("MaxNumOfSaveBackups")
    private Integer maxBackupQuantity = 20;
    //endregion


    GameUserSettingsINI gameUserSettingsINI = new GameUserSettingsINI();



    @ExtraCommandLineArgument(value = "NoBattlEye", invertBoolean = true)
    private boolean battlEye = false;

    @ExtraCommandLineArgument("newsaveformat")
    private boolean newGameSaveFormat = false;

    @ExtraCommandLineArgument("usestore")
    private boolean useStore = false;

    @ExtraCommandLineArgument("BackupTransferPlayerDatas")
    private boolean backupTransferPlayerData = false;

    @ExtraCommandLineArgument("EnableIdlePlayerKick")
    private boolean enableIdlePlayerKick = false;

    @ExtraCommandLineArgument("culture")
    private String culture = null;

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

        var extraArgsFields = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(it -> it.isAnnotationPresent(ExtraCommandLineArgument.class))
                .toList();

        for (var field : extraArgsFields) {
            var annotation = field.getAnnotation(ExtraCommandLineArgument.class);
            try {
                var argument = annotation.value().startsWith("-") ? annotation.value() : "-" + annotation.value();
                var value = field.get(this);

                // if the field is null or empty, skip it
                if (value == null || value.toString().isEmpty()) continue;

                if(value instanceof Boolean){
                    if(annotation.invertBoolean()) {
                        value = !(boolean) value;
                    }
                    if((boolean) value){
                        extraArgs.add(argument);
                    }
                }else{
                    extraArgs.add(argument + "=" + value.toString());
                }

            } catch (IllegalAccessException e) {
                log.error("Error while getting value of field: " + field.getName(), e);
            }
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
