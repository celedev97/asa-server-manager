package dev.cele.asa_sm.feign;

import dev.cele.asa_sm.dto.github.Release;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "github", url = "https://api.github.com")
public interface GithubClient {

    @GetMapping("/repos/{owner}/{repo}/releases/latest")
    Release getLatestRelease(@PathVariable String owner, @PathVariable String repo);

}
