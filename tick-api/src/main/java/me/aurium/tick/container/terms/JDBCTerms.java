package me.aurium.tick.container.terms;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import me.aurium.tick.container.CreationTerms;

public abstract class JDBCTerms implements CreationTerms {

    private final String db_name;
    private final String db_username;
    private final String db_password;

    protected JDBCTerms(String db_name, String db_username, String db_password) {
        this.db_name = db_name;
        this.db_username = db_username;
        this.db_password = db_password;
    }

    protected String getDb_name() {
        return db_name;
    }

    protected String getDb_username() {
        return db_username;
    }

    protected String getDb_password() {
        return db_password;
    }


}
