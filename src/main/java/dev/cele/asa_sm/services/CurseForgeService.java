package dev.cele.asa_sm.services;

import dev.cele.asa_sm.dto.curseforge.DataDto;
import dev.cele.asa_sm.dto.curseforge.GetModFilesRequestBody;
import dev.cele.asa_sm.dto.curseforge.ModDto;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.encoding.BaseRequestInterceptor;
import org.springframework.cloud.openfeign.encoding.FeignClientEncodingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "curseforge", url = "https://api.curseforge.com/v1", configuration = CurseForgeFeignClientConfiguration.class)
public interface CurseForgeService {

    @GetMapping("/mods/{modId}")
    DataDto<ModDto> getModById(@PathVariable Integer modId);


    @PostMapping("/mods")
    DataDto<List<ModDto>> getModsByIds(@RequestBody GetModFilesRequestBody getModFilesRequestBody);

    default DataDto<List<ModDto>> getModsByIds(List<Integer> modIds) {
        return getModsByIds(new GetModFilesRequestBody(modIds));
    }


}

class CurseForgeFeignClientConfiguration {
    @Value("${curseforge.api.token}")
    private String token;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-api-key", token);
        };
    }
}
