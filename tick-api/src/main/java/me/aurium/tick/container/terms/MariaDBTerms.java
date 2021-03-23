package me.aurium.tick.container.terms;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.container.JDBCContainer;
import me.aurium.tick.container.container.MariaDBContainer;

/**
 * mariadb
 *
 * external port is the exposed port of the mariadb container, but still
 */
public class MariaDBTerms implements JDBCTerms {

    private final String rootPassword;
    private final String databaseUsername;
    private final String databasePassword;
    private final String databaseName;
    private final String containerName;

    private final int portBinding;

    public MariaDBTerms(String rootPassword, String databaseUsername, String databasePassword, String databaseName, String containerName, int portBinding) {
        this.rootPassword = rootPassword;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.databaseName = databaseName;
        this.containerName = containerName;
        this.portBinding = portBinding;
    }

    @Override
    public int externalPort() {
        return portBinding;
    }

    @Override
    public String rootPassword() {
        return rootPassword;
    }

    @Override
    public String databaseUsername() {
        return databaseUsername;
    }

    @Override
    public String databasePassword() {
        return databasePassword;
    }

    @Override
    public String databaseName() {
        return databaseName;
    }

    @Override
    public String containerName() {
        return containerName;
    }

    @Override
    public JDBCContainer creation(DockerClient client, ContainerOptions options) {
        CreateContainerResponse response = client.createContainerCmd("mariadb:10.5.9")
                .withName(containerName)
                .withEnv(
                        "MYSQL_ROOT=" + rootPassword,
                        "MYSQL_DATABASE=" + databaseName,
                        "MYSQL_USER=" + databaseUsername,
                        "MYSQL_PASSWORD=" + databasePassword
                )
                .withHostConfig(new HostConfig()
                        .withPortBindings(PortBinding.parse(portBinding + ":3306"))
                )
                .exec();

         return new MariaDBContainer(client, options, response.getId(), this);
    }

}
