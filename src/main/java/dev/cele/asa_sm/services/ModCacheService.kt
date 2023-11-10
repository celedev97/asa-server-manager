package dev.cele.asa_sm.services

import com.fasterxml.jackson.databind.ObjectMapper
import dev.cele.asa_sm.Const.DATA_DIR
import dev.cele.asa_sm.Const.MOD_CACHE_DIR
import dev.cele.asa_sm.dto.curseforge.ModDto
import jakarta.annotation.PostConstruct
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

@Service
class ModCacheService(
    val curseForgeService: CurseForgeService,
    val objectMapper: ObjectMapper
) {

    @PostConstruct
    fun init() {
        MOD_CACHE_DIR.createDirectories()
    }

    fun getMods(modIds: String): List<ModDto> {
        val requestedMods = modIds.split(",").map { it.toInt() }
        val cachedMods = MOD_CACHE_DIR.toFile().listFiles()?.map { it.nameWithoutExtension.toInt() } ?: listOf()
        val missingModIds = requestedMods.filter { !cachedMods.contains(it) }

        if (missingModIds.isNotEmpty()) {
            val mods = curseForgeService.getModsByIds(missingModIds).data!!
            mods.forEach {
                objectMapper.writeValue(MOD_CACHE_DIR.resolve("${it.id}.json").toFile(), it);
            }
        }

        return requestedMods.map {
            objectMapper.readValue(MOD_CACHE_DIR.resolve("${it}.json").toFile(), ModDto::class.java)
        }
    }

}
