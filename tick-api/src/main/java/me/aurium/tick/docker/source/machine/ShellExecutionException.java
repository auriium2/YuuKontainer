package me.aurium.tick.docker.source.machine;

public class ShellExecutionException extends RuntimeException{

    public ShellExecutionException(Throwable throwable) {
        super(throwable);
    }

    public ShellExecutionException(String string) {
        super(string);
    }

}
