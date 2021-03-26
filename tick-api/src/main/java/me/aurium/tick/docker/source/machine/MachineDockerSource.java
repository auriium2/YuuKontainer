package me.aurium.tick.docker.source.machine;

import com.github.dockerjava.core.LocalDirectorySSLConfig;
import com.github.dockerjava.transport.SSLConfig;
import me.aurium.tick.docker.source.DockerSource;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

public class MachineDockerSource implements DockerSource {

    private final String executableName;
    private final String machineToUse;

    public MachineDockerSource(String executableName, String machineToUse) {
        this.executableName = executableName;
        this.machineToUse = machineToUse;
    }


    @Override
    public String getSourceURL() {

        try {
            ProcessResult result = new ProcessExecutor()
                    .command(executableName,"url", machineToUse)
                    .readOutput(true)
                    .exitValueNormal()
                    .execute();

            return result.outputString().replaceAll("\n","");

        } catch (InterruptedException | IOException | TimeoutException e) {
            throw new ShellExecutionException(e);
        }

    }

    @Override
    public String getSourceIP() {
        try {
            ProcessResult result = new ProcessExecutor()
                    .command(executableName,"ip", machineToUse)
                    .readOutput(true)
                    .exitValueNormal()
                    .execute();

            return result.outputString().replaceAll("\n","");

        } catch (InterruptedException | IOException | TimeoutException e) {
            throw new ShellExecutionException(e);
        }
    }

    public SSLConfig getSSLConfig() {
        //copied this from testcontainers, a248 please advise.
        return new LocalDirectorySSLConfig(Paths.get(System.getProperty("user.home") + "/.docker/machine/certs/").toString());
    }
}
