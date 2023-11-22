package dev.cele.asa_sm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettingsDto {
    private String theme = "classpath:/dev/cele/asa_sm/themes/nord.theme.json";
}
