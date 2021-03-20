package me.aurium.tick.docker.machine;

public class ShellExecutionException extends RuntimeException{

    ShellExecutionException(Throwable throwable) {
        super(throwable);
    }

    ShellExecutionException(String string) {
        super(string);
    }

}
