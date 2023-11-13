package dev.cele.asa_sm.services;

import dev.cele.asa_sm.Const;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static dev.cele.asa_sm.Const.ASA_STEAM_GAME_NUMBER;

@Service
@RequiredArgsConstructor
public class SteamCMDService {
    private final Logger logger;
    private final CommandRunnerService commandRunnerService;

    private final String WINDOWS_URL = "https://steamcdn-a.akamaihd.net/client/installer/steamcmd.zip";
    private final String STEAM_CMD_WIN_FOLDER = "steamcmd";
    private final String STEAM_CMD_WIN_PATH = STEAM_CMD_WIN_FOLDER + File.separator + "steamcmd.exe";


    @PostConstruct
    public void init() {
        logger.info("SteamCMDService initialized");
        //checking for install...

        var exists = false;

        if(SystemUtils.IS_OS_WINDOWS) {
            exists = new File(STEAM_CMD_WIN_PATH).exists();
        } else if(SystemUtils.IS_OS_LINUX) {
            var result = commandRunnerService.runCommand("which", "steamcmd");
            exists = result.getExitCode() == 0 && result.getOutput().contains("steamcmd");
        } else {
            logger.error("Unsupported OS");
            throw new RuntimeException("Unsupported OS");
        }

        if(exists) {
            logger.info("SteamCMD already installed");
        } else {
            logger.info("SteamCMD not installed. Installing...");
            install();
        }
    }

    //region SteamCMD Installation
    public void install() {
        if(SystemUtils.IS_OS_WINDOWS){
            windowsInstall();
        }else {
            logger.error("Unsupported OS");
            throw new RuntimeException("Unsupported OS");
        }
    }

    @SneakyThrows
    private void windowsInstall() {
        //get a temp dir
        var tempDir = SystemUtils.getJavaIoTmpDir().getAbsolutePath() + File.separator + "asa_sm";
        Files.createDirectories(Path.of(tempDir));

        //region download file from WINDOWS_URL to zipLocation
        logger.info("Downloading SteamCMD...");
        InputStream input = new URL(WINDOWS_URL).openStream();

        //download file to temp dir
        var zipLocation = tempDir + File.separator + "steamcmd.zip";
        Files.copy(input, Path.of(zipLocation), StandardCopyOption.REPLACE_EXISTING);
        input.close();

        logger.info("Download complete. Saved to "+zipLocation);
        //endregion

        //region unzip file
        logger.info("Unzipping "+zipLocation);
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipLocation));
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            var newFile = Path.of(tempDir + File.separator + zipEntry.getName());

            if (zipEntry.isDirectory()) {
                Files.createDirectories(newFile);
                logger.info("Created directory "+zipEntry.getName());
            } else {
                Files.createDirectories(newFile.getParent());

                // write file content
                Files.copy(zipInputStream, newFile, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Unzipped "+zipEntry.getName());
            }
        }
        //endregion

        //region move steamcmd.exe to STEAM_CMD_WIN_PATH
        var steamcmdDownloaded = Path.of(tempDir + File.separator + "steamcmd.exe");
        var steamcmdTarget = Path.of(STEAM_CMD_WIN_PATH);

        Files.createDirectories(steamcmdTarget.getParent());
        Files.move(steamcmdDownloaded, steamcmdTarget);

        logger.info("Moved steamcmd.exe to "+steamcmdTarget);
        //endregion
    }
    //endregion

    //region SteamCMD Commands

    @SneakyThrows
    public void runDownloadVerifyServer(String guid){
        var result = commandRunnerService.runCommand(downloadVerifyServerCommand(guid));

        if(result.getExitCode() != 0) {
            logger.error("Error downloading server: "+result.getExitCode());
            throw new RuntimeException("Error downloading server: "+result.getExitCode());
        }

        logger.info("Server downloaded to "+guid);
    }

    @SneakyThrows
    public String[] downloadVerifyServerCommand(String guid){
        var installDir = Const.SERVERS_DIR.resolve(guid);
        Files.createDirectories(installDir);
        var steamCMD = "steamcmd";
        if(SystemUtils.IS_OS_WINDOWS) {
            steamCMD = STEAM_CMD_WIN_PATH;
        }

        // steamcmd +force_install_dir ..\server\guid +login anonymous +app_update 2430930 validate +quit
        return new String[]{
                steamCMD,
                "+force_install_dir", installDir.toAbsolutePath().toString(),
                "+login", "anonymous",
                "+app_update", ASA_STEAM_GAME_NUMBER, "validate",
                "+quit"
        };
    }

    //endregion

}
