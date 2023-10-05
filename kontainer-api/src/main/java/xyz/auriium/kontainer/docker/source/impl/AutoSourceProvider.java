package xyz.auriium.kontainer.docker.source.impl;

import xyz.auriium.kontainer.docker.source.ApplicableResult;
import xyz.auriium.kontainer.docker.source.DockerSourceProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Automatic source provider that loops through a list of all existing providers in order to attempt delegation
 */
public class AutoSourceProvider {

    private static final Set<DockerSourceProvider> providers = new HashSet<>();

    static {
        providers.add(new WindowsSourceProvider());
        providers.add(new RootlessSourceProvider());
        providers.add(new UnixSourceProvider());
        providers.add(new SystemEnvSourceProvider());
    }


    /**
     * Attempts to get a docker source provider that will work with your machine.
     * @return a docker source provider
     * @throws NoProviderException if no valid provider can be found for use on your machine.
     */
    public DockerSourceProvider provide() {

        List<DockerSourceProvider> sorted = providers.stream().sorted((a, b) -> b.priority().compareTo(a.priority())).collect(Collectors.toList());

        for (DockerSourceProvider provider : sorted) {
            ApplicableResult result = provider.isApplicable(); {
                if (!result.isApplicable()) continue;

                return provider;
            }
        }

        throw new NoProviderException();
    }
}
