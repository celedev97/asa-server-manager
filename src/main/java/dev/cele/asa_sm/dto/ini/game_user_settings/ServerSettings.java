package dev.cele.asa_sm.dto.ini.game_user_settings;

import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;
import dev.cele.asa_sm.dto.ini.IniValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@IniSection("ServerSettings")
@Getter @Setter
public class ServerSettings {
    //General
    @IniValue("ActiveMods")
    private String activeMods = "";
    @IniValue("ActiveMapMod")
    private String activeMapMod = "";
    @IniValue("ActiveTotalConversion")
    private String activeTotalConversion = null;
    @IniValue("AdminLogging")
    private Boolean adminLogging = false;
    @IniValue("AllowAnyoneBabyImprintCuddle")
    private Boolean allowAnyoneBabyImprintCuddle = false;
    @IniValue("AllowCaveBuildingPvE")
    private Boolean allowCaveBuildingPvE = false;
    @IniValue("AllowCaveBuildingPvP")
    private Boolean allowCaveBuildingPvP = true;
    @IniValue("AllowCrateSpawnsOnTopOfStructures")
    private Boolean allowCrateSpawnsOnTopOfStructures = false;
    @IniValue("AllowFlyerCarryPvE")
    private Boolean allowFlyerCarryPvE = false;
    @IniValue("AllowFlyingStaminaRecovery")
    private Boolean allowFlyingStaminaRecovery = false;
    @IniValue("AllowHideDamageSourceFromLogs")
    private Boolean allowHideDamageSourceFromLogs = true;
    @IniValue("AllowHitMarkers")
    private Boolean allowHitMarkers = true;
    @IniValue("AllowIntegratedSPlusStructures")
    private Boolean allowIntegratedSPlusStructures = true;
    @IniValue("AllowMultipleAttachedC4")
    private Boolean allowMultipleAttachedC4 = false;
    @IniValue("AllowRaidDinoFeeding")
    private Boolean allowRaidDinoFeeding = false;
    @IniValue("AllowSharedConnections")
    private Boolean allowSharedConnections = false;
    @IniValue("AllowTekSuitPowersInGenesis")
    private Boolean allowTekSuitPowersInGenesis = false;
    @IniValue("AllowThirdPersonPlayer")
    private Boolean allowThirdPersonPlayer = true;
    @IniValue("AlwaysAllowStructurePickup")
    private Boolean alwaysAllowStructurePickup = true;
    @IniValue("AlwaysNotifyPlayerLeft")
    private Boolean alwaysNotifyPlayerLeft = false;
    @IniValue("AutoDestroyDecayedDinos")
    private Boolean autoDestroyDecayedDinos = false;
    @IniValue("AutoDestroyOldStructuresMultiplier")
    private Double autoDestroyOldStructuresMultiplier = 0.0;
    @IniValue("AutoSavePeriodMinutes")
    private Double autoSavePeriodMinutes = 15.0;
    @IniValue("BanListURL")
    private String banListURL = null;
    @IniValue("bForceCanRideFliers")
    private Boolean forceCanRideFliers = null;
    @IniValue("ClampItemSpoilingTimes")
    private Boolean clampItemSpoilingTimes = false;
    @IniValue("ClampItemStats")
    private Boolean clampItemStats = false;
    @IniValue("ClampResourceHarvestDamage")
    private Boolean clampResourceHarvestDamage = false;
    @IniValue("CustomDynamicConfigUrl")
    private String customDynamicConfigUrl = null;
    @IniValue("CustomLiveTuningUrl")
    private String customLiveTuningUrl = null;
    @IniValue("DayCycleSpeedScale")
    private Double dayCycleSpeedScale = 1.0;
    @IniValue("DayTimeSpeedScale")
    private Double dayTimeSpeedScale = 1.0;
    @IniValue("DestroyUnconnectedWaterPipes")
    private Boolean destroyUnconnectedWaterPipes = false;
    @IniValue("DifficultyOffset")
    private Double difficultyOffset = 1.0;
    @IniValue("DinoCharacterFoodDrainMultiplier")
    private Double dinoCharacterFoodDrainMultiplier = 1.0;
    @IniValue("DinoCharacterHealthRecoveryMultiplier")
    private Double dinoCharacterHealthRecoveryMultiplier = 1.0;
    @IniValue("DinoCharacterStaminaDrainMultiplier")
    private Double dinoCharacterStaminaDrainMultiplier = 1.0;
    @IniValue("DinoCountMultiplier")
    private Double dinoCountMultiplier = 1.0;
    @IniValue("DinoDamageMultiplier")
    private Double dinoDamageMultiplier = 1.0;
    @IniValue("DinoResistanceMultiplier")
    private Double dinoResistanceMultiplier = 1.0;
    @IniValue("DisableDinoDecayPvE")
    private Boolean disableDinoDecayPvE = false;
    @IniValue("DisableImprintDinoBuff")
    private Boolean disableImprintDinoBuff = false;
    @IniValue("DisablePvEGamma")
    private Boolean disablePvEGamma = false;
    @IniValue("DisableStructureDecayPvE")
    private Boolean disableStructureDecayPvE = false;
    @IniValue("DisableWeatherFog")
    private Boolean disableWeatherFog = false;
    @IniValue("DontAlwaysNotifyPlayerJoined")
    private Boolean dontAlwaysNotifyPlayerJoined = false;
    @IniValue("EnableExtraStructurePreventionVolumes")
    private Boolean enableExtraStructurePreventionVolumes = false;
    @IniValue("EnablePvPGamma")
    private Boolean enablePvPGamma = false;
    @IniValue("ExtinctionEventTimeInterval")
    private Long extinctionEventTimeInterval = 0L;
    @IniValue("FastDecayUnsnappedCoreStructures")
    private Boolean fastDecayUnsnappedCoreStructures = false;
    @IniValue("ForceAllStructureLocking")
    private Boolean forceAllStructureLocking = false;
    @IniValue("globalVoiceChat")
    private Boolean globalVoiceChat = false;
    @IniValue("HarvestAmountMultiplier")
    private Double harvestAmountMultiplier = 1.0;
    @IniValue("HarvestHealthMultiplier")
    private Double harvestHealthMultiplier = 1.0;
    @IniValue("IgnoreLimitMaxStructuresInRangeTypeFlag")
    private Boolean ignoreLimitMaxStructuresInRangeTypeFlag = false;
    @IniValue("ItemStackSizeMultiplier")
    private Double itemStackSizeMultiplier = 1.0;
    @IniValue("KickIdlePlayersPeriod")
    private Double kickIdlePlayersPeriod = 3600.0;
    @IniValue("MaxGateFrameOnSaddles")
    private Integer maxGateFrameOnSaddles = 0;
    @IniValue("MaxHexagonsPerCharacter")
    private Integer maxHexagonsPerCharacter = 2000000000;
    @IniValue("MaxPersonalTamedDinos")
    private Integer maxPersonalTamedDinos = 0;
    @IniValue("MaxPlatformSaddleStructureLimit")
    private Integer maxPlatformSaddleStructureLimit = 75;
    @IniValue("MaxTamedDinos")
    private Integer maxTamedDinos = 5000;
    @IniValue("MaxTributeCharacters")
    private Integer maxTributeCharacters = 10;
    @IniValue("MaxTributeDinos")
    private Integer maxTributeDinos = 20;
    @IniValue("MaxTributeItems")
    private Integer maxTributeItems = 50;
    @IniValue("NightTimeSpeedScale")
    private Double nightTimeSpeedScale = 1.0;
    @IniValue("NonPermanentDiseases")
    private Boolean nonPermanentDiseases = false;
    @IniValue("NPCNetworkStasisRangeScalePlayerCountStart")
    private Double nPCNetworkStasisRangeScalePlayerCountStart = 0.0;
    @IniValue("NPCNetworkStasisRangeScalePlayerCountEnd")
    private Double nPCNetworkStasisRangeScalePlayerCountEnd = 0.0;
    @IniValue("NPCNetworkStasisRangeScalePercentEnd")
    private Double nPCNetworkStasisRangeScalePercentEnd = 0.55;
    @IniValue("OnlyAutoDestroyCoreStructures")
    private Boolean onlyAutoDestroyCoreStructures = false;
    @IniValue("OnlyDecayUnsnappedCoreStructures")
    private Boolean onlyDecayUnsnappedCoreStructures = false;
    @IniValue("OverrideOfficialDifficulty")
    private Double overrideOfficialDifficulty = 1.0;
    @IniValue("OverrideStructurePlatformPrevention")
    private Boolean overrideStructurePlatformPrevention = false;
    @IniValue("OxygenSwimSpeedStatMultiplier")
    private Double oxygenSwimSpeedStatMultiplier = 1.0;
    @IniValue("PerPlatformMaxStructuresMultiplier")
    private Double perPlatformMaxStructuresMultiplier = 1.0;
    @IniValue("PersonalTamedDinosSaddleStructureCost")
    private Integer personalTamedDinosSaddleStructureCost = 0;
    @IniValue("PlatformSaddleBuildAreaBoundsMultiplier")
    private Double platformSaddleBuildAreaBoundsMultiplier = 1.0;
    @IniValue("PlayerCharacterFoodDrainMultiplier")
    private Double playerCharacterFoodDrainMultiplier = 1.0;
    @IniValue("PlayerCharacterHealthRecoveryMultiplier")
    private Double playerCharacterHealthRecoveryMultiplier = 1.0;
    @IniValue("PlayerCharacterStaminaDrainMultiplier")
    private Double playerCharacterStaminaDrainMultiplier = 1.0;
    @IniValue("PlayerCharacterWaterDrainMultiplier")
    private Double playerCharacterWaterDrainMultiplier = 1.0;
    @IniValue("PlayerDamageMultiplier")
    private Double playerDamageMultiplier = 1.0;
    @IniValue("PlayerResistanceMultiplier")
    private Double playerResistanceMultiplier = 1.0;
    @IniValue("PreventDiseases")
    private Boolean preventDiseases = false;
    @IniValue("PreventMateBoost")
    private Boolean preventMateBoost = false;
    @IniValue("PreventOfflinePvP")
    private Boolean preventOfflinePvP = false;
    @IniValue("PreventOfflinePvPInterval")
    private Double preventOfflinePvPInterval = 0.0;
    @IniValue("PreventSpawnAnimations")
    private Boolean preventSpawnAnimations = false;
    @IniValue("PreventTribeAlliances")
    private Boolean preventTribeAlliances = false;
    @IniValue("ProximityChat")
    private Boolean proximityChat = false;
    @IniValue("PvEAllowStructuresAtSupplyDrops")
    private Boolean pveAllowStructuresAtSupplyDrops = false;
    @IniValue("PvEDinoDecayPeriodMultiplier")
    private Double pveDinoDecayPeriodMultiplier = 1.0;
    @IniValue("PvEStructureDecayPeriodMultiplier")
    private Double pveStructureDecayPeriodMultiplier = 1.0;
    @IniValue("PvPDinoDecay")
    private Boolean pvpDinoDecay = false;
    @IniValue("PvPStructureDecay")
    private Boolean pvpStructureDecay = false;
    @IniValue("RaidDinoCharacterFoodDrainMultiplier")
    private Double raidDinoCharacterFoodDrainMultiplier = 1.0;
    @IniValue("RandomSupplyCratePoints")
    private Boolean randomSupplyCratePoints = false;
    @IniValue("RCONEnabled")
    private Boolean rconEnabled = false;
    @IniValue("RCONPort")
    private Integer rconPort = 27020;
    @IniValue("RCONServerGameLogBuffer")
    private Integer rconServerGameLogBuffer = 600;
    @IniValue("ResourcesRespawnPeriodMultiplier")
    private Double resourcesRespawnPeriodMultiplier = 1.0;
    @IniValue("ServerAdminPassword")
    private String serverAdminPassword = null;
    @IniValue("ServerAutoForceRespawnWildDinosInterval")
    private Float serverAutoForceRespawnWildDinosInterval = 0f;
    @IniValue("ServerCrosshair")
    private Boolean serverCrosshair = true;
    @IniValue("ServerForceNoHUD")
    private Boolean serverForceNoHUD = false;
    @IniValue("ServerHardcore")
    private Boolean serverHardcore = false;
    @IniValue("ServerPassword")
    private String serverPassword = null;
    @IniValue("serverPVE")
    private Boolean serverPVE = false;
    @IniValue("ShowFloatingDamageText")
    private Boolean showFloatingDamageText = false;
    @IniValue("ShowMapPlayerLocation")
    private Boolean showMapPlayerLocation = true;
    @IniValue("SpectatorPassword")
    private String spectatorPassword = null;
    @IniValue("StructureDamageMultiplier")
    private Double structureDamageMultiplier = 1.0;
    @IniValue("StructurePickupHoldDuration")
    private Double structurePickupHoldDuration = 0.5;
    @IniValue("StructurePickupTimeAfterPlacement")
    private Double structurePickupTimeAfterPlacement = 30.0;
    @IniValue("StructurePreventResourceRadiusMultiplier")
    private Double structurePreventResourceRadiusMultiplier = 1.0;
    @IniValue("StructureResistanceMultiplier")
    private Double structureResistanceMultiplier = 1.0;
    @IniValue("TamedDinoDamageMultiplier")
    private Double tamedDinoDamageMultiplier = 1.0;
    @IniValue("TamedDinoResistanceMultiplier")
    private Double tamedDinoResistanceMultiplier = 1.0;
    @IniValue("TamingSpeedMultiplier")
    private Double tamingSpeedMultiplier = 1.0;
    @IniValue("TheMaxStructuresInRange")
    private Integer theMaxStructuresInRange = 10500;
    @IniValue("TribeLogDestroyedEnemyStructures")
    private Boolean tribeLogDestroyedEnemyStructures = false;
    @IniValue("TribeNameChangeCooldown")
    private Double tribeNameChangeCooldown = 15.0;
    @IniValue("UseFjordurTraversalBuff")
    private Boolean useFjordurTraversalBuff = false;
    @IniValue("UseOptimizedHarvestingHealth")
    private Boolean useOptimizedHarvestingHealth = false;
    @IniValue("XPMultiplier")
    private Double xPMultiplier = 1.0;

    //CrossARK Transfers
    @IniValue("CrossARKAllowForeignDinoDownloads")
    private Boolean crossARKAllowForeignDinoDownloads = false;
    @IniValue("MinimumDinoReuploadInterval")
    private Double minimumDinoReuploadInterval = 0.0;
    @IniValue("noTributeDownloads")
    private Boolean noTributeDownloads = false;
    @IniValue("PreventDownloadDinos")
    private Boolean preventDownloadDinos = false;
    @IniValue("PreventDownloadItems")
    private Boolean preventDownloadItems = false;
    @IniValue("PreventDownloadSurvivors")
    private Boolean preventDownloadSurvivors = false;
    @IniValue("PreventUploadDinos")
    private Boolean preventUploadDinos = false;
    @IniValue("PreventUploadItems")
    private Boolean preventUploadItems = false;
    @IniValue("PreventUploadSurvivors")
    private Boolean preventUploadSurvivors = false;
    @IniValue("TributeCharacterExpirationSeconds")
    private Integer tributeCharacterExpirationSeconds = 0;
    @IniValue("TributeDinoExpirationSeconds")
    private Integer tributeDinoExpirationSeconds = 86400;
    @IniValue("TributeItemExpirationSeconds")
    private Integer tributeItemExpirationSeconds = 86400;

    //Cryo Sickness and Cryopod Nerf
    @IniValue("CryopodNerfDamageMult")
    private Double cryopodNerfDamageMult = 0.01;
    @IniValue("CryopodNerfDuration")
    private Double cryopodNerfDuration = 0.0;
    @IniValue("CryopodNerfIncomingDamageMultPercent")
    private Double cryopodNerfIncomingDamageMultPercent = 0.25;
    @IniValue("EnableCryopodNerf")
    private Boolean enableCryopodNerf = false;
    @IniValue("EnableCryoSicknessPVE")
    private Boolean enableCryoSicknessPVE = false;

    //Text Filtering
    @IniValue("BadWordListURL")
    private String badWordListURL = "http://arkdedicated.com/badwords.txt";
    @IniValue("BadWordWhiteListURL")
    private String badWordWhiteListURL = "http://arkdedicated.com/badwords.txt";
    @IniValue("bFilterCharacterNames")
    private Boolean filterCharacterNames = false;
    @IniValue("bFilterChat")
    private Boolean filterChat = false;
    @IniValue("bFilterTribeNames")
    private Boolean filterTribeNames = false;

    //Deprecated options
//    @IniValue("AllowDeprecatedStructures")
//    private Boolean allowDeprecatedStructures = false;
//    @IniValue("bAllowFlyerCarryPVE")
//    private Boolean allowFlyerCarryPVE = false;
//    @IniValue("bDisableStructureDecayPvE")
//    private Boolean disableStructureDecayPvE = false;
//    @IniValue("ForceFlyerExplosives")
//    private Boolean forceFlyerExplosives = false;
//    @IniValue("MaxStructuresInRange")
//    private Integer maxStructuresInRange = 1300;
//    @IniValue("NewMaxStructuresInRange")
//    private Integer newMaxStructuresInRange = 6000;
//    @IniValue("PvEStructureDecayDestructionPeriod")
//    private Integer pvEStructureDecayDestructionPeriod = 0;

    //Undocumented options
    @IniValue("AllowedCheatersURL")
    private String allowedCheatersURL = null;
    @IniValue("ChatLogFileSplitIntervalSeconds")
    private Integer chatLogFileSplitIntervalSeconds = 86400;
    @IniValue("ChatLogFlushIntervalSeconds")
    private Integer chatLogFlushIntervalSeconds = 86400;
    @IniValue("ChatLogMaxAgeInDays")
    private Integer chatLogMaxAgeInDays = 5;
    @IniValue("DontRestoreBackup")
    private Boolean dontRestoreBackup = false;
    @IniValue("EnableAFKKickPlayerCountPercent")
    private Double enableAFKKickPlayerCountPercent = 0.0;
    @IniValue("EnableMeshBitingProtection")
    private Boolean enableMeshBitingProtection = true;
    @IniValue("FreezeReaperPregnancy")
    private Boolean freezeReaperPregnancy = false;
    @IniValue("LogChatMessages")
    private Boolean logChatMessages = false;
    @IniValue("MaxStructuresInSmallRadius")
    private Integer maxStructuresInSmallRadius = 0;
    @IniValue("MaxStructuresToProcess")
    private Integer maxStructuresToProcess = 0;
    @IniValue("PreventOutOfTribePinCodeUse")
    private Boolean preventOutOfTribePinCodeUse = false;
    @IniValue("RadiusStructuresInSmallRadius")
    private Double radiusStructuresInSmallRadius = 0.0;
    @IniValue("ServerEnableMeshChecking")
    private Boolean serverEnableMeshChecking = false;
    @IniValue("TribeMergeAllowed")
    private Boolean tribeMergeAllowed = true;
    @IniValue("TribeMergeCooldown")
    private Double tribeMergeCooldown = 0.0;
    @IniValue("UpdateAllowedCheatersInterval")
    private Double updateAllowedCheatersInterval = 600.0;
    @IniValue("UseExclusiveList")
    private Boolean useExclusiveList = false;

    @IniExtraMap
    Map<String, String> extraIniMap;

}
