package dev.cele.asa_sm.services;

import dev.cele.asa_sm.SimpleLogger;
import dev.cele.asa_sm.dto.ProcessOutputDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

@Service
public class CommandRunnerService {

    SimpleLogger logToConsole = new SimpleLogger() {
        final Logger logger = LoggerFactory.getLogger(CommandRunnerService.class);

        @Override
        public void info(String message) {
            logger.info(message);
        }

        @Override
        public void error(String message) {
            logger.error(message);
        }
    };



    public ProcessOutputDto runCommand(String... commandAndArgs) {
        return runCommand(logToConsole, commandAndArgs);
    }

    public ProcessOutputDto runCommand(SimpleLogger log, String...  commandAndArgs) {
        log.info("Running command: " + String.join(" ", commandAndArgs));

        var output = new ProcessOutputDto();

        try {
            ProcessBuilder pb = new ProcessBuilder().command(commandAndArgs);
            Process p = pb.start();

            var infoStream = p.getInputStream();
            var errorStream = p.getErrorStream();

            var fullOutput = new StringBuilder();

            var threadInfo = new Thread(() -> {
                try (var scanner = new Scanner(infoStream)) {
                    while (scanner.hasNextLine()) {
                        synchronized (fullOutput){
                            var line = scanner.nextLine();
                            fullOutput.append(line).append("\n");
                            log.info(line);
                        }
                    }
                }
            });

            var threadError = new Thread(() -> {
                try (var scanner = new Scanner(errorStream)) {
                    while (scanner.hasNextLine()) {
                        synchronized (fullOutput){
                            var line = scanner.nextLine();
                            fullOutput.append(line).append("\n");
                            log.error(line);
                        }
                    }
                }
            });

            threadInfo.start();
            threadError.start();

            threadInfo.join();
            threadError.join();

            output.setExitCode(p.waitFor());
            output.setOutput(fullOutput.toString().trim());

            log.info("Command finished");
            return output;
        } catch (Exception e) {
            log.error("Error running command", e);
        }

        output.setExitCode(-1);
        return output;
    }




}
