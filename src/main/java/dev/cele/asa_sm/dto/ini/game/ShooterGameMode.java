package dev.cele.asa_sm.dto.ini.game;

import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;
import dev.cele.asa_sm.dto.ini.IniValue;

import java.util.Map;

/*
Attribute index table
This table shows relationships between each stats attribute and its coded index

Index	Stats
0	 Health
1	 Stamina /  Charge Capacity
2	 Torpidity
3	 Oxygen /  Charge Regeneration
4	 Food
5	 Water
6	Temperature
7	 Weight
8	 Melee Damage /  Charge Emission Range
9	 Movement Speed /  Maewing's Nursing effectiveness
10	 Fortitude
11	 Crafting Speed

MutagenLevelBoost[<Stat_ID>]=<integer>
MutagenLevelBoost_Bred[<Stat_ID>]=<integer>

PlayerBaseStatMultipliers[<attribute>]=<multiplier>
* */


@IniSection("/Script/ShooterGame.ShooterGameMode")
public class ShooterGameMode {

    //MutagenLevelBoost default values: 5, 5, 0, 0, 0, 0, 0, 5, 5, 0, 0, 0
    @IniValue("MutagenLevelBoost[0]")
    private Integer mutagenLevelBoostHealth = 5;

    @IniValue("MutagenLevelBoost[1]")
    private Integer mutagenLevelBoostStamina = 5;

    @IniValue("MutagenLevelBoost[2]")
    private Integer mutagenLevelBoostTorpidity = 0;

    @IniValue("MutagenLevelBoost[3]")
    private Integer mutagenLevelBoostOxygen = 0;

    @IniValue("MutagenLevelBoost[4]")
    private Integer mutagenLevelBoostFood = 0;

    @IniValue("MutagenLevelBoost[5]")
    private Integer mutagenLevelBoostWater = 0;

@   IniValue("MutagenLevelBoost[6]")
    private Integer mutagenLevelBoostTemperature = 0;

    @IniValue("MutagenLevelBoost[7]")
    private Integer mutagenLevelBoostWeight = 5;

    @IniValue("MutagenLevelBoost[8]")
    private Integer mutagenLevelBoostMeleeDamage = 5;

    @IniValue("MutagenLevelBoost[9]")
    private Integer mutagenLevelBoostMovementSpeed = 0;

    @IniValue("MutagenLevelBoost[10]")
    private Integer mutagenLevelBoostFortitude = 0;

    @IniValue("MutagenLevelBoost[11]")
    private Integer mutagenLevelBoostCraftingSpeed = 0;

    //MutagenLevelBoost_Bred default values: 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0
    @IniValue("MutagenLevelBoost_Bred[0]")
    private Integer mutagenLevelBoostBredHealth = 1;

    @IniValue("MutagenLevelBoost_Bred[1]")
    private Integer mutagenLevelBoostBredStamina = 1;

    @IniValue("MutagenLevelBoost_Bred[2]")
    private Integer mutagenLevelBoostBredTorpidity = 0;

    @IniValue("MutagenLevelBoost_Bred[3]")
    private Integer mutagenLevelBoostBredOxygen = 0;

    @IniValue("MutagenLevelBoost_Bred[4]")
    private Integer mutagenLevelBoostBredFood = 0;

    @IniValue("MutagenLevelBoost_Bred[5]")
    private Integer mutagenLevelBoostBredWater = 0;

    @IniValue("MutagenLevelBoost_Bred[6]")
    private Integer mutagenLevelBoostBredTemperature = 0;

    @IniValue("MutagenLevelBoost_Bred[7]")
    private Integer mutagenLevelBoostBredWeight = 1;

    @IniValue("MutagenLevelBoost_Bred[8]")
    private Integer mutagenLevelBoostBredMeleeDamage = 1;

    @IniValue("MutagenLevelBoost_Bred[9]")
    private Integer mutagenLevelBoostBredMovementSpeed = 0;

    @IniValue("MutagenLevelBoost_Bred[10]")
    private Integer mutagenLevelBoostBredFortitude = 0;

    @IniValue("MutagenLevelBoost_Bred[11]")
    private Integer mutagenLevelBoostBredCraftingSpeed = 0;

    //region Player Stats
    //PlayerBaseStatMultipliers, default values: all 1.0
    @IniValue("PlayerBaseStatMultipliers[0]")
    private Double playerBaseStatMultipliersHealth = 1.0;

    @IniValue("PlayerBaseStatMultipliers[1]")
    private Double playerBaseStatMultipliersStamina = 1.0;

    @IniValue("PlayerBaseStatMultipliers[2]")
    private Double playerBaseStatMultipliersTorpidity = 1.0;

    @IniValue("PlayerBaseStatMultipliers[3]")
    private Double playerBaseStatMultipliersOxygen = 1.0;

    @IniValue("PlayerBaseStatMultipliers[4]")
    private Double playerBaseStatMultipliersFood = 1.0;

    @IniValue("PlayerBaseStatMultipliers[5]")
    private Double playerBaseStatMultipliersWater = 1.0;

    @IniValue("PlayerBaseStatMultipliers[6]")
    private Double playerBaseStatMultipliersTemperature = 1.0;

    @IniValue("PlayerBaseStatMultipliers[7]")
    private Double playerBaseStatMultipliersWeight = 1.0;

    @IniValue("PlayerBaseStatMultipliers[8]")
    private Double playerBaseStatMultipliersMeleeDamage = 1.0;

    @IniValue("PlayerBaseStatMultipliers[9]")
    private Double playerBaseStatMultipliersMovementSpeed = 1.0;

    @IniValue("PlayerBaseStatMultipliers[10]")
    private Double playerBaseStatMultipliersFortitude = 1.0;

    @IniValue("PlayerBaseStatMultipliers[11]")
    private Double playerBaseStatMultipliersCraftingSpeed = 1.0;

    //PerLevelStatsMultiplier_Player, default values: all 1.0
    @IniValue("PerLevelStatsMultiplier_Player[0]")
    private Double perLevelStatsMultiplierPlayerHealth = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[1]")
    private Double perLevelStatsMultiplierPlayerStamina = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[2]")
    private Double perLevelStatsMultiplierPlayerTorpidity = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[3]")
    private Double perLevelStatsMultiplierPlayerOxygen = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[4]")
    private Double perLevelStatsMultiplierPlayerFood = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[5]")
    private Double perLevelStatsMultiplierPlayerWater = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[6]")
    private Double perLevelStatsMultiplierPlayerTemperature = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[7]")
    private Double perLevelStatsMultiplierPlayerWeight = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[8]")
    private Double perLevelStatsMultiplierPlayerMeleeDamage = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[9]")
    private Double perLevelStatsMultiplierPlayerMovementSpeed = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[10]")
    private Double perLevelStatsMultiplierPlayerFortitude = 1.0;

    @IniValue("PerLevelStatsMultiplier_Player[11]")
    private Double perLevelStatsMultiplierPlayerCraftingSpeed = 1.0;

    //endregion

    //region Dino stats
    //PerLevelStatsMultiplier_DinoWild, default all 1
    @IniValue("PerLevelStatsMultiplier_DinoWild[0]")
    private Double perLevelStatsMultiplierDinoWildHealth = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[1]")
    private Double perLevelStatsMultiplierDinoWildStamina = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[2]")
    private Double perLevelStatsMultiplierDinoWildTorpidity = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[3]")
    private Double perLevelStatsMultiplierDinoWildOxygen = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[4]")
    private Double perLevelStatsMultiplierDinoWildFood = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[5]")
    private Double perLevelStatsMultiplierDinoWildWater = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[6]")
    private Double perLevelStatsMultiplierDinoWildTemperature = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[7]")
    private Double perLevelStatsMultiplierDinoWildWeight = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[8]")
    private Double perLevelStatsMultiplierDinoWildMeleeDamage = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[9]")
    private Double perLevelStatsMultiplierDinoWildMovementSpeed = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[10]")
    private Double perLevelStatsMultiplierDinoWildFortitude = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoWild[11]")
    private Double perLevelStatsMultiplierDinoWildCraftingSpeed = 1.0;


    //PerLevelStatsMultiplier_DinoTamed default: 0.2 health, 0.17 damage, all the others 1
    @IniValue("PerLevelStatsMultiplier_DinoTamed[0]")
    private Double perLevelStatsMultiplierDinoTamedHealth = 0.2;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[1]")
    private Double perLevelStatsMultiplierDinoTamedStamina = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[2]")
    private Double perLevelStatsMultiplierDinoTamedTorpidity = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[3]")
    private Double perLevelStatsMultiplierDinoTamedOxygen = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[4]")
    private Double perLevelStatsMultiplierDinoTamedFood = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[5]")
    private Double perLevelStatsMultiplierDinoTamedWater = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[6]")
    private Double perLevelStatsMultiplierDinoTamedTemperature = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[7]")
    private Double perLevelStatsMultiplierDinoTamedWeight = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[8]")
    private Double perLevelStatsMultiplierDinoTamedMeleeDamage = 0.17;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[9]")
    private Double perLevelStatsMultiplierDinoTamedMovementSpeed = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[10]")
    private Double perLevelStatsMultiplierDinoTamedFortitude = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed[11]")
    private Double perLevelStatsMultiplierDinoTamedCraftingSpeed = 1.0;

    //PerLevelStatsMultiplier_DinoTamed_Add: 0.14 health, 0.14 damage, all the others 1
    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[0]")
    private Double perLevelStatsMultiplierDinoTamedAddHealth = 0.14;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[1]")
    private Double perLevelStatsMultiplierDinoTamedAddStamina = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[2]")
    private Double perLevelStatsMultiplierDinoTamedAddTorpidity = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[3]")
    private Double perLevelStatsMultiplierDinoTamedAddOxygen = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[4]")
    private Double perLevelStatsMultiplierDinoTamedAddFood = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[5]")
    private Double perLevelStatsMultiplierDinoTamedAddWater = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[6]")
    private Double perLevelStatsMultiplierDinoTamedAddTemperature = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[7]")
    private Double perLevelStatsMultiplierDinoTamedAddWeight = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[8]")
    private Double perLevelStatsMultiplierDinoTamedAddMeleeDamage = 0.14;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[9]")
    private Double perLevelStatsMultiplierDinoTamedAddMovementSpeed = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[10]")
    private Double perLevelStatsMultiplierDinoTamedAddFortitude = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Add[11]")
    private Double perLevelStatsMultiplierDinoTamedAddCraftingSpeed = 1.0;

    //PerLevelStatsMultiplier_DinoTamed_Affinity: 0.44 health, 0.44 damage, all the others 1
    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[0]")
    private Double perLevelStatsMultiplierDinoTamedAffinityHealth = 0.44;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[1]")
    private Double perLevelStatsMultiplierDinoTamedAffinityStamina = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[2]")
    private Double perLevelStatsMultiplierDinoTamedAffinityTorpidity = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[3]")
    private Double perLevelStatsMultiplierDinoTamedAffinityOxygen = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[4]")
    private Double perLevelStatsMultiplierDinoTamedAffinityFood = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[5]")
    private Double perLevelStatsMultiplierDinoTamedAffinityWater = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[6]")
    private Double perLevelStatsMultiplierDinoTamedAffinityTemperature = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[7]")
    private Double perLevelStatsMultiplierDinoTamedAffinityWeight = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[8]")
    private Double perLevelStatsMultiplierDinoTamedAffinityMeleeDamage = 0.44;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[9]")
    private Double perLevelStatsMultiplierDinoTamedAffinityMovementSpeed = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[10]")
    private Double perLevelStatsMultiplierDinoTamedAffinityFortitude = 1.0;

    @IniValue("PerLevelStatsMultiplier_DinoTamed_Affinity[11]")
    private Double perLevelStatsMultiplierDinoTamedAffinityCraftingSpeed = 1.0;
    //endregion

    @IniExtraMap
    Map<String, String> extraIniMap;
}
