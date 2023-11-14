package dev.cele.asa_sm.services;

import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.dto.github.Release;
import dev.cele.asa_sm.feign.GithubClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateService {
    private final GithubClient githubClient;

    @Value("${application.version}")
    private String currentVersion;

    private String ignoredVersion = "";
    {
        try {
            var ignoredVersionFile = Const.DATA_DIR.resolve("ignoredVersion.txt");
            if(ignoredVersionFile.toFile().exists() && ignoredVersionFile.toFile().isFile()){
                ignoredVersion = new String(Files.readAllBytes(ignoredVersionFile));
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error while reading ignoredVersion.txt: " + e.getMessage());
            log.error("Error while reading ignoredVersion.txt: ", e);
        }
    }

    public void checkForUpdates() {
        Release latestRelease = null;

        try {
            latestRelease = githubClient.getLatestRelease("celedev97", "asa-server-manager");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error while checking for updates: " + e.getMessage());
            log.error("Error while checking for updates: ", e);
        }

        assert latestRelease != null;
        var newVersion = latestRelease.getTag_name();

        if(ignoredVersion.equals(newVersion)){
            return;
        }

        //regex for version number with optional starting v and with optional snapshot version, one capturing group for major, one for minor, one for patch
        var versionRegex = Pattern.compile("v?(\\d+)\\.(\\d+)\\.(\\d+)(-SNAPSHOT)?");

        var currentVersionMatcher = versionRegex.matcher(currentVersion);
        var newVersionMatcher = versionRegex.matcher(newVersion);

        //extract version numbers from version strings
        currentVersionMatcher.find();
        newVersionMatcher.find();

        var currentMajor = Integer.parseInt(currentVersionMatcher.group(1));
        var currentMinor = Integer.parseInt(currentVersionMatcher.group(2));
        var currentPatch = Integer.parseInt(currentVersionMatcher.group(3));

        var newMajor = Integer.parseInt(newVersionMatcher.group(1));
        var newMinor = Integer.parseInt(newVersionMatcher.group(2));
        var newPatch = Integer.parseInt(newVersionMatcher.group(3));

        //check if new version is newer than current version
        if(newMajor > currentMajor || newMinor > currentMinor || newPatch > currentPatch){
            String[] options = new String[] {"Yes", "Ask me Later", "Ignore this version"};
            int response = JOptionPane.showOptionDialog(
                    null,
                    "A new version of ASA Server Manager is available: " + newVersion + "\nDo you want to download it?",
                    "New Version Available",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if(response == 0){
                try {
                    Desktop.getDesktop().browse(URI.create(latestRelease.getAssets()[0].getBrowser_download_url()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error while opening download link: " + e.getMessage());
                    log.error("Error while opening download link: ", e);
                }
                System.exit(0);
            }else if(response == 2){
                try {
                    Files.writeString(Const.DATA_DIR.resolve("ignoredVersion.txt"), newVersion);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error while writing ignoredVersion.txt: " + e.getMessage());
                    log.error("Error while writing ignoredVersion.txt: ", e);
                }
            }
        }
    }

}
