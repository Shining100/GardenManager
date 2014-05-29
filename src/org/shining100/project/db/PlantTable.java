package org.shining100.project.db;

import java.sql.PreparedStatement;
import java.util.LinkedList;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by yangguang on 2014/5/27.
 */
public class PlantTable {
    public PlantTable() throws SQLException {
        conn = ConnectionPool.getInstance().createConnection();
    }

    public void close() throws SQLException {
        conn.close();
    }

//    void insert(PlantRecord plantRecord) throws SQLException {
//        PreparedStatement statement = conn.prepareStatement(insertSql);
//        statement.setString(1, plantRecord.name);
//        statement.setString(2, plantRecord.description);
//        statement.setInt(3, plantRecord.lightType);
//        statement.executeUpdate();
//    }

    Connection conn;
    static final String insertSql = "insert into plant values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
}
