package xyz.auriium.tick.plugin;

import xyz.auriium.tick.CommonTickFactory;
import xyz.auriium.tick.Tick;
import xyz.auriium.tick.TickFactory;
import xyz.auriium.tick.container.ContainerOptions;
import xyz.auriium.tick.container.container.JDBCContainer;
import xyz.auriium.tick.container.terms.JDBCTerms;
import xyz.auriium.tick.docker.image.CachedPullStrategyProvider;
import xyz.auriium.tick.docker.source.CreationOptions;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.docker.source.impl.MachineSourceProvider;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.INITIALIZE)
public class PopulateGoalMojo extends AbstractTickMojo{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute() throws MojoFailureException {
        TickFactory factory = new CommonTickFactory(
                new MachineSourceProvider(defaultDockerName),
                new CachedPullStrategyProvider(), new CreationOptions(usePostCreationTest, true),
                new ContainerOptions(300)
        );

        Initializer init = null;

        //i hate this but it doesnt let me use lambdas because its fucking retarded
        if (initializer.equals(CommonInits.MARIA_DB)) {
            init = new MariaDBInitializer();
        } else {
            throw new MojoFailureException("incorrect init provided, no such init exists!");
        }

        JDBCTerms terms = init.getTerms(dbName,dbUsername,dbPassword,dbPort);

        Tick tick = factory.produce();
        try (JDBCContainer container = tick.getManager().startupContainer(terms)) {
            logger.info(container.getJDBCUrl());


        } catch (Exception e) {
            logger.error("Error occurred during lifecycle: ", e);
        }

    }
}
