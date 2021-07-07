package xyz.auriium.tick.docker.source;

public interface DockerSourceProvider {

    String name();
    Integer priority();

    /**
     * Attempts to generate a DockerSource. Typically calls {@link #isApplicable()} internally.
     * @return a docker source.
     * @throws SourceProvideException if an error occurs attempting to generate the dockersource outlined.
     */
    DockerSource source(ClientOptions options) throws SourceProvideException;

    /**
     * Checks whether or not the DockerSourceProvider can effectively provide a DockerSource
     * @return Result containing whether or not a Source can be provided and a reason for it.
     */
    ApplicableResult isApplicable();

}
