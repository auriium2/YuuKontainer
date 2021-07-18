package xyz.auriium.tick.container;

public class CreationOptions {



    private final boolean usePostCreationTest;
    private final boolean withTLS;

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
