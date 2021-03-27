package me.aurium.tick.container.terms.mariadb;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.container.JDBCContainer;
import me.aurium.tick.container.container.MariaDBContainer;
import me.aurium.tick.container.terms.JDBCTerms;
import me.aurium.tick.container.terms.termParts.Arguments;
import me.aurium.tick.container.terms.termParts.ArgumentsObject;
import me.aurium.tick.docker.source.DockerLocation;

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

        this.args = new ArgumentsObject.Builder()
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
    public Arguments creationArguments() {
        return args;
    }

    @Override
    public JDBCContainer creation(DockerLocation location, DockerClient client, ContainerOptions options, String id) {
         return new MariaDBContainer(location, client, options, id, config);
    }

}
