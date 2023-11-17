package dev.cele.asa_sm.services;

import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.dto.github.Release;
import dev.cele.asa_sm.feign.GithubClient;
import dev.cele.asa_sm.ui.frames.ProgressFrame;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateService {
    private final GithubClient githubClient;

    @Getter
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

        if(currentVersion.contains("-SNAPSHOT")){
            //don't check update if this is a snapshot
            log.info("This is a snapshot version, skipping update check");
            return;
        }

        log.info("Checking for updates...");

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
        var versionRegex = Pattern.compile("v?(\\d+)\\.(\\d+)\\.(\\d+)");

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
                    downloadUpdate(latestRelease);
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

    @SneakyThrows
    private void downloadUpdate(Release latestRelease) {
        if(SystemUtils.IS_OS_WINDOWS){
            var asset = Arrays.stream(latestRelease.getAssets())
                    .filter(a -> a.getName().endsWith(".exe"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No exe asset found"));


            log.info("Downloading update...");

            InputStream input = new URL(asset.getBrowser_download_url()).openStream();
            var tempFile = File.createTempFile("asa_sm_update", ".exe");

            //file copy from input to tempFile with progress bar

            FileOutputStream output = new FileOutputStream(tempFile);

            var progressFrame = new ProgressFrame(null, "Downloading Update", "Downloading update...", false);
            progressFrame.launch(prog -> {
                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalBytesRead = 0;
                long fileSize = asset.getSize();
                try{
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;

                        progressFrame.setProgress(
                                (int) (totalBytesRead * 100 / fileSize)
                        );
                        log.info("Downloaded " + totalBytesRead + " bytes out of " + fileSize + " bytes (" + (totalBytesRead * 100 / fileSize) + "%)");

                        progressFrame.repaint();

                    }

                    input.close();
                    output.close();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Error while downloading update: " + e.getMessage());
                    log.error("Error while downloading update: ", e);
                }
            });
            progressFrame.dispose();




            input.close();

            //run cmd /c start "" "path/to/file.exe" /SILENT
            log.info("Starting update process");
            log.info("File path: " + tempFile.getAbsolutePath());

            // wait 4 seconds, and then run the installer, meanwhile close the current app to avoid file locking
            new ProcessBuilder(
                    tempFile.getAbsolutePath(), "/SILENT", "/FORCECLOSEAPPLICATIONS", "/RESTARTAPPLICATIONS"
            ).start();
            System.exit(0);
        }else{
            //open browser
            Desktop.getDesktop().browse(new URI(latestRelease.getHtml_url()));
        }
    }

}
