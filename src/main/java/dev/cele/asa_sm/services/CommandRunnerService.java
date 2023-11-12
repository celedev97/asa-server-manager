package dev.cele.asa_sm.services;

import dev.cele.asa_sm.dto.ProcessOutputDto;
import dev.cele.asa_sm.SimpleLogger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CommandRunnerService {

    private final SimpleLogger logToConsole = new SimpleLogger() {
        private final org.slf4j.Logger logger = LoggerFactory.getLogger(CommandRunnerService.class);

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

    public ProcessOutputDto runCommand(SimpleLogger log, String... commandAndArgs) {
        log.info("Running command: " + String.join(" ", commandAndArgs));

        ProcessOutputDto output = new ProcessOutputDto();

        ProcessBuilder pb = new ProcessBuilder().command(commandAndArgs);
        Process p = null;

        try {
            p = pb.start();

            Scanner infoStream = new Scanner(p.getInputStream());
            Scanner errorStream = new Scanner(p.getErrorStream());

            StringBuilder fullOutput = new StringBuilder();

            Thread threadInfo = new Thread(() -> {
                while (infoStream.hasNextLine()) {
                    synchronized (fullOutput) {
                        String line = infoStream.nextLine();
                        fullOutput.append(line).append("\n");
                        log.info(line);
                    }
                }
            });

            Thread threadError = new Thread(() -> {
                while (errorStream.hasNextLine()) {
                    synchronized (fullOutput) {
                        String line = errorStream.nextLine();
                        fullOutput.append(line).append("\n");
                        log.error(line);
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
        } catch (InterruptedException e) {
            if (p != null) {
                p.destroy();
            }
        } catch (Exception e) {
            log.error("Error running command", e);
        }
        output.setExitCode(-1);
        return output;
    }
}
