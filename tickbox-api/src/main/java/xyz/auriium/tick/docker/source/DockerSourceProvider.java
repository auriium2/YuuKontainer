package xyz.auriium.tick.docker.source;

import xyz.auriium.tick.container.CreationOptions;

public interface DockerSourceProvider extends Comparable<DockerSourceProvider> {

    String name();

    /**
     * Priority to be tested by {@link xyz.auriium.tick.docker.source.impl.AutoSourceProvider}
     * The higher the number the earlier it is tested.
     *
     * @return
     */
    Integer priority();

    /**
     * Attempts to generate a DockerSource. Do not call this without calling {@link #isApplicable()}
     * @return a docker source.
     * @throws IllegalStateException if an error occurs attempting to generate the dockersource outlined.
     */
    DockerSource source(CreationOptions options);

    /**
     * Checks whether or not the DockerSourceProvider can effectively provide a DockerSource
     * @return Result containing whether or not a Source can be provided and a reason for it.
     */
    ApplicableResult isApplicable();

    @Override
    default int compareTo(DockerSourceProvider o) {
        return this.priority().compareTo(o.priority());
    }
}
