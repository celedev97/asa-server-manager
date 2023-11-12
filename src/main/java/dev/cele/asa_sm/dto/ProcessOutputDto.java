package dev.cele.asa_sm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProcessOutputDto {
    private String output = "";
    private int exitCode = 0;
}