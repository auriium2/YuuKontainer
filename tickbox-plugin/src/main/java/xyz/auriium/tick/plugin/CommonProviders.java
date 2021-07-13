package xyz.auriium.tick.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.docker.source.ApplicableResult;
import xyz.auriium.tick.docker.source.DockerSourceProvider;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class used to search for a valid provider - Unique to the maven plugin!
 */
public class CommonProviders {

    CommonProviders() {}

    public static final CommonProviders INSTANCE = new CommonProviders(); //Limit instances, use ugly static pattern to allow outside
    private static final Logger logger = LoggerFactory.getLogger("(TICK | PROVIDER SEARCHER)");
    //classes to insert their own providers
    private final Set<DockerSourceProvider> providers = new HashSet<>();

    public CommonProviders install(DockerSourceProvider provider) {
        providers.add(provider);

        return this;
    }

    /**
     * Attempts to find a DockerProvider with the name given, or returns a default name and logs.
     * @param preferred the preferred Provider identifier to use
     * @return a provider
     * @throws IllegalStateException if no providers can be found
     */
    public DockerSourceProvider find(String preferred) {
        for (DockerSourceProvider provider : providers) {
            if (provider.name().equalsIgnoreCase(preferred)) {
                return provider;
            }
        }

        return find();
    }

    /**
     * Attempts to find a DockerProvider using the first available highest priority provider
     * @return a provider
     * @throws IllegalStateException if no providers can be found.
     */
    public DockerSourceProvider find() {

        for (DockerSourceProvider provider : providers.stream()
                .sorted(Comparator.comparing(DockerSourceProvider::priority))
                .collect(Collectors.toList())) {

            ApplicableResult result = provider.isApplicable();

            if (result.isApplicable()) {
                return provider;
            } else {
                logger.debug(String.format("Attempt to produce valid DockerClient failure \nwith provider [%s], \nReason: [%s]", provider.name(), result.getReason()));
            }
        }


        throw new IllegalStateException("No providers exist in the CommonProviders library that will work on your system!");
    }

}
