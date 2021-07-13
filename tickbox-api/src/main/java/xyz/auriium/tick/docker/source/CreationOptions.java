package xyz.auriium.tick.docker.source;

/**
 * Options that pertain to the creation of the docker client
 */
public class CreationOptions {

    private final boolean usePostCreationTest;
    private final boolean withTLS;

    /**
     * Option details for client options
     * @param usePostCreationTest whether to use the post creation test
     *                            to check if a completed docker client actually works.
     *                            This is tested by creating a new container and immediately killing it.
     *
     * @param withTLS whether to use TLS with created containers or not
     *
     */
    public CreationOptions(boolean usePostCreationTest, boolean withTLS) {
        this.usePostCreationTest = usePostCreationTest;
        this.withTLS = withTLS;
    }

    public boolean isUsePostCreationTest() {
        return usePostCreationTest;
    }

    public boolean isWithTLS() {
        return withTLS;
    }

    public static CreationOptions defaults() {
        return new CreationOptions(true, false);
    }
}
