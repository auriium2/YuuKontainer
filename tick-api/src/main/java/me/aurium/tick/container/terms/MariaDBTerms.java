package me.aurium.tick.container.terms;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import me.aurium.tick.container.CreationTerms;

public class MariaDBTerms extends JDBCTerms {

    protected MariaDBTerms(String db_name, String db_username, String db_password) {
        super(db_name, db_username, db_password);
    }

    @Override
    public CreateContainerResponse creation(DockerClient client) {
        return null;
    }
}
