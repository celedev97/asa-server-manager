package dev.cele.asa_sm.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.dto.curseforge.ModDto;
import dev.cele.asa_sm.feign.CurseForgeClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ModCacheService {

    private final CurseForgeClient curseForgeClient;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        Path modCacheDir = Const.MOD_CACHE_DIR;
        try {
            Files.createDirectories(modCacheDir);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public List<ModDto> getMods(String modIds) {
        List<Integer> requestedMods = Stream.of(modIds.split(","))
                .map(Integer::parseInt)
                .toList();

        File[] cachedModFiles = Const.MOD_CACHE_DIR.toFile().listFiles();
        List<Integer> cachedMods = (cachedModFiles != null) ?
                Stream.of(cachedModFiles)
                        .map(file -> Integer.parseInt(file.getName().replaceFirst("[.][^.]+$", "")))
                        .toList() :
                List.of();

        List<Integer> missingModIds = requestedMods.stream()
                .filter(modId -> !cachedMods.contains(modId))
                .collect(Collectors.toList());

        if (!missingModIds.isEmpty()) {
            curseForgeClient.getModsByIds(missingModIds).getData().forEach(mod -> {
                try {
                    objectMapper.writeValue(Const.MOD_CACHE_DIR.resolve(mod.getId() + ".json").toFile(), mod);
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the exception according to your needs
                }
            });
        }

        return requestedMods.stream()
                .map(modId -> {
                    try {
                        return objectMapper.readValue(Const.MOD_CACHE_DIR.resolve(modId + ".json").toFile(), ModDto.class);
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle the exception according to your needs
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
