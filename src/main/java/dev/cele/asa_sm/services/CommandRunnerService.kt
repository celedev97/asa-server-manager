package dev.cele.asa_sm.services

import dev.cele.asa_sm.SimpleLogger
import dev.cele.asa_sm.dto.ProcessOutputDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommandRunnerService {

    var logToConsole = object : SimpleLogger {
        val logger = LoggerFactory.getLogger(CommandRunnerService::class.java)
        override fun info(message: String) {
            logger.info(message)
        }

        override fun error(message: String) {
            logger.error(message)
        }
    }

    fun runCommand(vararg commandAndArgs: String?): ProcessOutputDto {
        return runCommand(logToConsole, *commandAndArgs)
    }

    fun runCommand(log: SimpleLogger, vararg commandAndArgs: String?): ProcessOutputDto {
        log.info("Running command: " + java.lang.String.join(" ", *commandAndArgs))

        val output = ProcessOutputDto()

        val pb = ProcessBuilder().command(*commandAndArgs)
        val p = pb.start()

        try {
            val infoStream = p.inputStream
            val errorStream = p.errorStream

            val fullOutput = StringBuilder()

            val threadInfo = Thread {
                Scanner(infoStream).use { scanner ->
                    while (scanner.hasNextLine()) {
                        synchronized(fullOutput) {
                            val line = scanner.nextLine()
                            fullOutput.append(line).append("\n")
                            log.info(line)
                        }
                    }
                }
            }

            val threadError = Thread {
                Scanner(errorStream).use { scanner ->
                    while (scanner.hasNextLine()) {
                        synchronized(fullOutput) {
                            val line = scanner.nextLine()
                            fullOutput.append(line).append("\n")
                            log.error(line)
                        }
                    }
                }
            }

            threadInfo.start()
            threadError.start()

            threadInfo.join()
            threadError.join()

            output.exitCode = p.waitFor()
            output.output = fullOutput.toString().trim()

            log.info("Command finished")
            return output
        } catch (e: InterruptedException) {
            p.destroy()
        } catch (e: Exception) {
            log.error("Error running command", e)
        }
        output.exitCode = -1
        return output
    }

}
