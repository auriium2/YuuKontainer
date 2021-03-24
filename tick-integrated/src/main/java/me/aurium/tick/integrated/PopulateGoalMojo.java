package me.aurium.tick.integrated;

import me.aurium.tick.CommonTickFactory;
import me.aurium.tick.Tick;
import me.aurium.tick.TickFactory;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.container.JDBCContainer;
import me.aurium.tick.container.terms.JDBCTerms;
import me.aurium.tick.docker.source.ClientOptions;
import me.aurium.tick.docker.source.machine.MachineSourceProvider;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.INITIALIZE)
public class PopulateGoalMojo extends AbstractTickMojo{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute() throws MojoFailureException {
        TickFactory factory = new CommonTickFactory(
                new MachineSourceProvider(defaultDockerName),
                new ClientOptions(false),
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

        try (Tick tick = factory.produce()) {
            JDBCContainer container = tick.getManager().produceContainer(terms);





        } catch (Exception e) {
            logger.error("Error occurred during tick lifecycle: ",e);
        }
    }
}
