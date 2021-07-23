package xyz.auriium.tick.containers.sql.mariadb;

import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.tick.centralized.ResourceManager;
import xyz.auriium.tick.containers.sql.JDBCConfig;
import xyz.auriium.tick.containers.sql.JDBCContainer;
import xyz.auriium.tick.containers.sql.JDBCTerms;
import xyz.auriium.tick.container.Arguments;
import xyz.auriium.tick.docker.source.DockerSource;

import java.util.Optional;

/**
 * mariadb
 *
 * external port is the exposed port of the mariadb container, but still
 *
 * TODO: image searching and guaruntees
 */
public class MariaDBTerms implements JDBCTerms {

    private final JDBCConfig config;
    private final Arguments args;

    public MariaDBTerms(String rootPassword, String databaseUsername, String databasePassword, String databaseName, String containerName, int portBinding) {
        this.config = new JDBCConfig(rootPassword, databaseUsername, databasePassword, databaseName, containerName, portBinding);

        this.args = new Arguments.Builder()
                .withBinding(PortBinding.parse(portBinding + ":3306"))
                .withImage("mariadb:10.5.9")
                .withParams(
                        "MYSQL_ROOT=" + rootPassword,
                        "MYSQL_DATABASE=" + databaseName,
                        "MYSQL_USER=" + databaseUsername,
                        "MYSQL_PASSWORD=" + databasePassword
                )
                .withCreationName(containerName)
                .build();
    }


    @Override
    public String getDockerImageName() {
        return args.getDockerImageName();
    }

    @Override
    public String[] getParameters() {
        return args.getParameters();
    }

    @Override
    public Optional<PortBinding> getBinding() {
        return Optional.of(args.getBinding());
    }

    @Override
    public String getContainerName() {
        return args.getContainerName();
    }

    @Override
    public JDBCContainer instantiateHolder(DockerSource location, ResourceManager manager, String id) {
         return new MariaDBContainer(manager, location, id, config);
    }

}
