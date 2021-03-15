package me.aurium.tick.full;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.flywaydb.core.Flyway;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.testcontainers.containers.JdbcDatabaseContainer;

public class PopulateGoalMojo extends AbstractTickMojo{


    @Override
    public void execute() throws MojoExecutionException,MojoFailureException {

        getLog().info("(TICK) Initializing TestContainer!");

        try (JdbcDatabaseContainer<?> construct = initializer.initializeContainer("username","password","sandbox")) {

            getLog().info("(TICK) Starting TestContainer!");

            construct.start();

            String url = construct.getJdbcUrl();
            String username = construct.getUsername();
            String password = construct.getPassword();

            getLog().info("URL: " + url + " USERNAME: " + username + " PASSWORD: " + password);

            getLog().info("(TICK) TestContainer successfully deployed! Loading Flyway!");

            if (locations == null) throw new MojoFailureException("No locations to draw sources from!");

            Flyway flyway = Flyway.configure(getClass().getClassLoader())
                    .dataSource(url,username,password)
                    .locations(locations)
                    .validateMigrationNaming(true).group(true)
                    .load();

            getLog().info("(TICK) Flyway successfully loaded! Migrating to testcontainer now!");

            flyway.migrate();

            getLog().info("(TICK) Flyway successfully migrated! Now activating JOOQ configuration!");

            Configuration configuration = new Configuration()
                    .withJdbc(new Jdbc()
                            .withDriver(construct.getDriverClassName())
                            .withUrl(url)
                            .withUser(username)
                            .withPassword(password))
                    .withGenerator(new Generator()
                            .withDatabase(new Database()
                                    .withName(initializer.correspondingJooqClassName())
                                    .withIncludes(".*")
                                    .withExcludes("")
                                    .withInputSchema(construct.getDatabaseName())) //TODO testing
                            .withTarget(new Target()
                                    .withPackageName(packageName)
                                    .withDirectory(outputDirectory)));

            getLog().info("(TICK) Configuration successful! Activating JOOQ Code Generation!");

            GenerationTool.generate(configuration);

            getLog().info("(TICK) Code generation finished! Manually closing test container!");

            construct.stop();

            getLog().info("(TICK) Tick generation finished successfully! (?) Please check your target directory to ensure satisfaction!");

        } catch (Exception e) {
            throw new MojoFailureException(e.getMessage());
        }


    }
}
