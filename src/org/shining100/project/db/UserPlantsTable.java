package org.shining100.project.db;

import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Created by yangguang on 2014/5/29.
 */
public class UserPlantsTable {
    public UserPlantsTable() throws SQLException {
        conn = ConnectionPool.getInstance().createConnection();
    }

    public void close() throws SQLException {
        if (null != insertStmt) insertStmt.close();
        if (null != deleteStmt) deleteStmt.close();
        if (null != getPlantsStmt) getPlantsStmt.close();
        conn.close();
    }

    public void insert(UserPlantsRecord record) throws SQLException {
        if (null == insertStmt) insertStmt = conn.prepareStatement(insertSql);
        insertStmt.setInt(1, record.getUserId());
        insertStmt.setInt(2, record.getPlantId());
        insertStmt.setString(3, record.getName());
        insertStmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        if (null == deleteStmt) deleteStmt = conn.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        deleteStmt.executeUpdate();
    }

    public LinkedList<UserPlantsRecord> getPlants(int userId) throws SQLException {
        if (null == getPlantsStmt) getPlantsStmt = conn.prepareStatement(getPlantsSql);
        getPlantsStmt.setInt(1, userId);
        ResultSet rs = getPlantsStmt.executeQuery();
        LinkedList<UserPlantsRecord> records = new LinkedList<UserPlantsRecord>();
        while (rs.next()) {
            UserPlantsRecord record = new UserPlantsRecord();
            record.setId(rs.getInt(1));
            record.setUserId(userId);
            record.setPlantId(rs.getInt(2));
            record.setName(rs.getString(3));
            records.push(record);
        }
        return records;
    }

    Connection conn;
    static final String insertSql = "insert into user_plants (user_id, plant_id, name) values(?, ?, ?)";
    PreparedStatement insertStmt;
    static final String deleteSql = "delete from user_plants where id = ?";
    PreparedStatement deleteStmt;
    static final String getPlantsSql = "select id, plant_id, name from user_plants where user_id = ?";
    PreparedStatement getPlantsStmt;
}
