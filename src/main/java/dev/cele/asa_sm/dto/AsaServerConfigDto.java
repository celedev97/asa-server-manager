package dev.cele.asa_sm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.dto.ini.game_user_settings.GameUserSettingsINI;
import dev.cele.asa_sm.enums.MapsEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

@Getter
@Setter
@FieldNameConstants
@Slf4j
public class AsaServerConfigDto {

    //region stuff for management of profiles
    private Boolean justImported = false;
    private String customInstallPath = null;

    public Path getServerPath(){
        if(customInstallPath != null){
            return Path.of(customInstallPath);
        }else{
            return Const.SERVERS_DIR.resolve(guid);
        }
    }

    //endregion

    //region stuff for the unsaved variable
    @JsonIgnore
    private Boolean unsaved = false;
    public void setUnsaved(Boolean value){
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
    @ExtraCommandLineArgument("WinLiveMaxPlayers") @IgnoreIfEqual(type = Integer.class, value = "70")
    private int serverMaxPlayers = 70;

    private Boolean rconEnabled = false;
    private int rconPort = 32330;
    private int rconServerLogBuffer = 600;

    @ExtraCommandLineArgument("mods")
    private String modIds = "";

    @ExtraCommandLineArgument("MaxNumOfSaveBackups") @IgnoreIfEqual(type = Integer.class, value = "20")
    private Integer maxBackupQuantity = 20;
    //endregion


    GameUserSettingsINI gameUserSettingsINI = new GameUserSettingsINI();



    @ExtraCommandLineArgument(value = "NoBattlEye", invertBoolean = true)
    private Boolean battlEye = false;

    @ExtraCommandLineArgument("EnableIdlePlayerKick")
    private Boolean enableIdlePlayerKick = false;

    @ExtraCommandLineArgument("culture")
    private String culture = null;

    @ExtraCommandLineArgument(value = "insecure")
    private Boolean vacDisabled = false;

    @ExtraCommandLineArgument("noantispeedhack")
    private Boolean antiSpeedHackDisabled = false;

    @ExtraCommandLineArgument("speedhackbias") @IgnoreIfEqual(type = Double.class, value = "1.0")
    private Double speedHackBias = 1.0;

    @ExtraCommandLineArgument("nocombineclientmoves")
    private Boolean disablePlayerMovePhysicsOptimization = false;

    @ExtraCommandLineArgument("servergamelog")
    private Boolean serverGameLog = false;

    @ExtraCommandLineArgument("NoHangDetection")
    private Boolean disableHangDetection = false;

    @ExtraCommandLineArgument("nodinos")
    private Boolean disableDinos = false;

    @ExtraCommandLineArgument("noundermeshchecking")
    private Boolean noUnderMeshChecking = false;

    @ExtraCommandLineArgument("noundermeshkilling")
    private Boolean noUnderMeshKilling = false;

    @ExtraCommandLineArgument("AllowSharedConnections")
    private Boolean allowSharedConnections = false;

    @ExtraCommandLineArgument("SecureSendArKPayload")
    private Boolean creatureUploadIssueProtection = false;

    @ExtraCommandLineArgument("UseSecureSpawnRules")
    private Boolean secureItemDinoSpawnRules = false;

    @ExtraCommandLineArgument("UseItemDupeCheck")
    private Boolean additionalDupeProtection = false;

    @ExtraCommandLineArgument("ForceRespawnDinos")
    private Boolean forceRespawnDinos = false;

    @ExtraCommandLineArgument("StasisKeepControllers")
    private Boolean stasisKeepControllers = false;

    @ExtraCommandLineArgument("structurememopts")
    private Boolean structureMemoryOptimizations = false;

    @ExtraCommandLineArgument("UseStructureStasisGrid")
    private Boolean useStructureStasisGrid = false;

    @ExtraCommandLineArgument("servergamelogincludetribelogs")
    private Boolean serverGameLogIncludeTribeLogs = false;

    @ExtraCommandLineArgument("ServerRCONOutputTribeLogs")
    private Boolean serverRCONOutputTribeLogs = false;

    @ExtraCommandLineArgument("AdminLogging")
    private Boolean adminLogsToPublicChat = false;

    @ExtraCommandLineArgument("NotifyAdminCommandsInChat")
    private Boolean adminLogsToAdminChat = false;


    private String additionalServerArgs = "";

    @JsonIgnore
    public String[] getCommand() {
        List<String> mainArgs = new ArrayList<>();

        mainArgs.add(map);
        mainArgs.add("listen");

        //name and password
        if (!serverName.isEmpty()) mainArgs.add("SessionName=" + serverName);

        if (!StringUtils.isEmpty(serverPassword)) mainArgs.add("ServerPassword=" + serverPassword);
        if (!StringUtils.isEmpty(serverAdminPassword)) mainArgs.add("ServerAdminPassword=" + serverAdminPassword);
        if (!StringUtils.isEmpty(serverSpectatorPassword)) mainArgs.add("SpectatorPassword=" + serverSpectatorPassword);

        //networking
        if (serverPort != 0) mainArgs.add("Port=" + serverPort);
        if (serverQueryPort != 0) mainArgs.add("QueryPort=" + serverQueryPort);

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

                //check if there's an ignoreIf annotation
                var ignoreIf = field.getAnnotation(IgnoreIfEqual.class);
                if(ignoreIf != null){
                    var ignoreIfValue = ignoreIf.value();
                    var realValue = field.get(this);

                    var ignore = false;

                    ignore = Objects.equals(ignoreIfValue, realValue.toString());

                    if(ignore) continue;
                }

                // if the field is null or empty, skip it
                if (value == null || value.toString().isEmpty()) continue;

                if(value instanceof Boolean){
                    if(annotation.invertBoolean()) {
                        value = !(Boolean) value;
                    }
                    if((Boolean) value){
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
                getServerPath()
                        .resolve("ShooterGame/Binaries/Win64/ArkAscendedServer.exe")
                        .toAbsolutePath()
                        .toString()
        );
        finalCommand.add(String.join("?", mainArgs));
        finalCommand.addAll(extraArgs);

        return finalCommand.toArray(new String[0]);
    }

}
