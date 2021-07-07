package xyz.auriium.tick.docker.source.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.docker.source.ApplicableResult;
import xyz.auriium.tick.docker.source.DockerSourceProvider;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonProviders {

    CommonProviders() {}

    public static final CommonProviders INSTANCE = new CommonProviders(); //Limit instances, use ugly static pattern to allow outside
    private static final Logger logger = LoggerFactory.getLogger("TickBox - Docker Providers");
    //classes to insert their own providers
    private final Set<DockerSourceProvider> providers = new HashSet<>();

    public CommonProviders install(DockerSourceProvider provider) {
        providers.add(provider);

        return this;
    }

    public DockerSourceProvider find() {

        for (DockerSourceProvider provider : providers.stream()
                .sorted(Comparator.comparing(DockerSourceProvider::priority))
                .collect(Collectors.toList())) {

            ApplicableResult result = provider.isApplicable();

            if (result.isApplicable()) {
                return provider;
            } else {
                logger.warn(String.format("Attempt to produce valid DockerClient failure for provider [%s], Reason: [%s]", provider.name(), result.getReason()));
            }
        }


        throw new IllegalStateException("No providers exist in the CommonProviders library that will work on your system!");
    }

}
