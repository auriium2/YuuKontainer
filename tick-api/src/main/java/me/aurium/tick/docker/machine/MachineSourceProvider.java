package me.aurium.tick.docker.machine;

import me.aurium.tick.docker.DockerSource;
import me.aurium.tick.docker.DockerSourceProvider;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

public class MachineSourceProvider implements DockerSourceProvider {

    private final String preferredDockerProvider;

    private static final String DEFAULT = "default";

    private final Logger logger = LoggerFactory.getLogger(MachineSourceProvider.class);

    public MachineSourceProvider(String preferredDockerProvider) {
        this.preferredDockerProvider = preferredDockerProvider;
    }

    @Override
    public Optional<DockerSource> source() {
        //TODO: make this better somehow. I don't know how, since at some point everything has to be stringly typed.

        logger.info("Attempting to produce MachineDockerSource using commandline!");

        String executableName = "docker-machine";
        if (SystemUtils.IS_OS_WINDOWS) {
            executableName="docker-machine.exe";
        }

        if (!executableExists(executableName)) return Optional.empty();

        try {
            ProcessResult result = new ProcessExecutor()
                    .command(executableName,"ls","-q", "--filter state=\"Running\"")
                    .readOutput(true)
                    .exitValueNormal()
                    .execute();

            List<String> executables = List.of(result.outputUTF8().split("\n"));

            if (executables.contains(preferredDockerProvider)) {
                logger.info("Preferred docker instance found on DockerMachine! Using instance: " + preferredDockerProvider);

                return Optional.of(new MachineDockerSource(executableName,preferredDockerProvider));
            } else if (executables.contains(DEFAULT)) {
                logger.info("Preferred docker instance was not found, using default instance!");

                return Optional.of(new MachineDockerSource(executableName,DEFAULT));
            } else {
                logger.error("No running docker was found!");

                return Optional.empty();
            }

        } catch (IOException | InterruptedException | TimeoutException e) {
            throw new ShellExecutionException(e);
        }

    }

    private boolean executableExists(String executable) {

        File directFile = new File(executable);
        if (directFile.exists() && directFile.canExecute()) {
            return true;
        }

        for (String pathString : System.getenv("PATH").split(Pattern.quote(File.pathSeparator))) {
            Path path = Paths.get(pathString);
            if (Files.exists(path.resolve(executable)) && Files.isExecutable(path.resolve(executable))) {
                return true;
            }
        }

        return false;
    }
}
