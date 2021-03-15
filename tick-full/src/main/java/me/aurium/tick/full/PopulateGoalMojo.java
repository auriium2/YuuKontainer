package me.aurium.tick.full;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.flywaydb.core.Flyway;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.testcontainers.containers.JdbcDatabaseContainer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.INITIALIZE)
public class PopulateGoalMojo extends AbstractTickMojo{

    @Override
    public void execute() throws MojoExecutionException,MojoFailureException {

        getLog().info("(TICK) Initializing TestContainer!");

        try (JdbcDatabaseContainer<?> construct = getInitializer().initializeContainer("username","password","sandbox")) {

            getLog().info("(TICK) Starting TestContainer!");

            construct.start();

            String url = construct.getJdbcUrl();
            String username = construct.getUsername();
            String password = construct.getPassword();

            getLog().info("URL: " + url + " USERNAME: " + username + " PASSWORD: " + password);

            getLog().info("(TICK) TestContainer successfully deployed! Loading Flyway!");

            if (getLocations() == null) throw new MojoFailureException("No locations to draw sources from!");

            Flyway flyway = Flyway.configure(getClass().getClassLoader())
                    .dataSource(url,username,password)
                    .locations(getLocations())
                    .validateMigrationNaming(true).group(true)
                    .load();

            getLog().info("(TICK) Flyway successfully loaded! Migrating to testcontainer now!");

            flyway.migrate();

            getLog().info("(TICK) Flyway successfully migrated! Now activating JOOQ configuration!");

            String output = getParsedOutput();


            Configuration configuration = new Configuration()
                    .withJdbc(new Jdbc()
                            .withDriver(construct.getDriverClassName())
                            .withUrl(url)
                            .withUser(username)
                            .withPassword(password))
                    .withGenerator(new Generator()
                            .withDatabase(new Database()
                                    .withName(getInitializer().correspondingJooqClassName())
                                    .withIncludes(".*")
                                    .withExcludes("")
                                    .withInputSchema(construct.getDatabaseName())) //TODO testing
                            .withTarget(new Target()
                                    .withPackageName(getPackageName())
                                    .withDirectory(output)));

            getLog().info("(TICK) Configuration successful! Activating JOOQ Code Generation!");

            GenerationTool.generate(configuration);

            getLog().info("(TICK) Code generation finished!");

            getLog().info("(TICK) Tick generation finished successfully! (?) Please check your target directory to ensure satisfaction!");

        } catch (Exception e) {
            throw new MojoFailureException(e.getMessage());
        } finally {

            // removes the shutdown hooks from testcontainers threads because they will fail after mojo closure context closes.
            // this is obviously horrible, but it stops us from getting pwned by classloader exceptions after successful builds.

            //fixme tbh i'll have to sort this out some time or another - or replace testcontainers with something more appropriate for my use case.
            //likely best to replace this with some sort of direct Docker shit

            try {
                Class<?> clazz = Class.forName("java.lang.ApplicationShutdownHooks");
                Field field = clazz.getDeclaredField("hooks");
                field.setAccessible(true);
                Map<Thread, Thread> hooks = (Map<Thread, Thread>) field.get(null);
                // Need to create a new map or else we'll get CMEs
                Map<Thread, Thread> hookMap = new HashMap<>();
                hooks.forEach(hookMap::put);

                hookMap.forEach((thread, hook) -> {
                    if (thread.getThreadGroup().getName().toLowerCase().contains("testcontainers")) {
                        Runtime.getRuntime().removeShutdownHook(hook);
                    }
                });
            } catch (Exception e) {
                throw new MojoExecutionException("Issue with testcontainers: ", e);
            }
        }


    }

    private final static String FILE_SYSTEM = "filesystem:";

    String getParsedOutput() throws MojoFailureException {
        if (getOutputDirectory().startsWith(FILE_SYSTEM)) {
            return getOutputDirectory().substring(FILE_SYSTEM.length());
        } else {
            throw new MojoFailureException("Output directory is not a correct directory type! (E.g. filesystem:)");
        }
    }
}
