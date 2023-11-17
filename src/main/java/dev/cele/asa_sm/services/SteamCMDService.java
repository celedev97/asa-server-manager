package dev.cele.asa_sm.services;

import dev.cele.asa_sm.Const;
import dev.cele.asa_sm.ui.frames.ProgressFrame;
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
import static dev.cele.asa_sm.Const.TEMP_DIR;

@Service
@RequiredArgsConstructor
public class SteamCMDService {
    private final Logger logger;
    private final CommandRunnerService commandRunnerService;

    private final String WINDOWS_URL = "https://steamcdn-a.akamaihd.net/client/installer/steamcmd.zip";
    private final Path STEAM_CMD_WIN_FOLDER = Path.of("steamcmd");
    private final File STEAM_CMD_WIN_EXE_FILE = STEAM_CMD_WIN_FOLDER.resolve("steamcmd.exe").toFile();


    public void checkSteamCMD() {
        logger.info("SteamCMDService initialized");
        //checking for install...

        var exists = false;

        if(SystemUtils.IS_OS_WINDOWS) {
            exists = STEAM_CMD_WIN_EXE_FILE.exists();
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
        Files.createDirectories(TEMP_DIR);

        //region download file from WINDOWS_URL to zipLocation
        logger.info("Downloading SteamCMD...");
        InputStream input = new URL(WINDOWS_URL).openStream();
        var zipLocation = Files.createTempFile(TEMP_DIR, "steamcmd", ".zip");

        //download file to temp dir
        var progressFrame = new ProgressFrame(null, "Downloading SteamCMD", "Downloading SteamCMD...", false);
        progressFrame.launch(pf -> {
            try {
                //download file from input to zipLocation with progress bar
                FileOutputStream output = new FileOutputStream(zipLocation.toFile());

                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalBytesRead = 0;
                long fileSize = input.available();

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    progressFrame.setProgress(
                            (int) (totalBytesRead * 100 / fileSize)
                    );
                    logger.info("Downloaded " + totalBytesRead + " bytes out of " + fileSize + " bytes (" + (totalBytesRead * 100 / fileSize) + "%)");

                    progressFrame.repaint();
                }

                input.close();
                output.close();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });
        logger.info("Download complete. Saved to "+zipLocation);
        //endregion

        //region unzip file
        logger.info("Unzipping "+zipLocation+" to "+STEAM_CMD_WIN_FOLDER);
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipLocation.toFile()));
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            var newFile = STEAM_CMD_WIN_FOLDER.resolve(zipEntry.getName());

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


        if(STEAM_CMD_WIN_EXE_FILE.exists()){
            logger.info("SteamCMD installed successfully");
        }else{
            logger.error("Error installing SteamCMD");
            throw new RuntimeException("Error installing SteamCMD");
        }
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
            steamCMD = STEAM_CMD_WIN_EXE_FILE.getAbsolutePath();
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
