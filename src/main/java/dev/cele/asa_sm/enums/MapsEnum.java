package dev.cele.asa_sm.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MapsEnum {
    THE_ISLAND("TheIsland_WP"),
    ;

    @Getter
    private final String mapName;
}
