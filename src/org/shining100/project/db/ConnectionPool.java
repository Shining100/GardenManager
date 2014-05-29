package org.shining100.project.db;

import java.util.Properties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * Created by yangguang on 2014/5/27.
 */
public class ConnectionPool {
    private ConnectionPool() {
        if (instance != null) throw new IllegalStateException("ConnectionPool had already created.");
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void init(String ip, int port, String user, String password) {
        url = "jdbc:mysql://" + ip + ":" + port + "/garden_manager";
        connProp.clear();
        connProp.put("user", user);
        connProp.put("password", password);
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, connProp);
    }

    String url;
    Properties connProp = new Properties();

    static final ConnectionPool instance = new ConnectionPool();
}
