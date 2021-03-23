package me.aurium.tick.docker.source.machine;

import me.aurium.tick.docker.source.DockerSource;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

import java.io.IOException;
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

            return result.outputUTF8();

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

            return result.outputUTF8();

        } catch (InterruptedException | IOException | TimeoutException e) {
            throw new ShellExecutionException(e);
        }
    }
}
