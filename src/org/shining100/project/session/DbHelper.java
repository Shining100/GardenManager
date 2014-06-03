package org.shining100.project.session;

import java.util.LinkedList;

import java.sql.SQLException;

import org.shining100.project.db.UserTable;
import org.shining100.project.db.UserRecord;
import org.shining100.project.db.UserPlantsTable;
import org.shining100.project.db.UserPlantsRecord;

/**
 * Created by yangguang on 2014/6/3.
 */
public class DbHelper {
    public static UserRecord getUser(String name) throws SQLException {
        UserTable userTable = null;
        UserRecord userRecord = null;
        try {
            userTable = new UserTable();
            userRecord = userTable.getUser(name);
            if (null != userRecord) return userRecord;
            userRecord = new UserRecord();
            userRecord.setName(name);
            userRecord.setPassword(name);
            userTable.insert(userRecord);
            userRecord = userTable.getUser(name);
        } finally {
            if (null != userTable) userTable.close();
        }
        return userRecord;
    }

    public static LinkedList<UserPlantsRecord> getUserPlants(String name) throws SQLException {
        UserRecord userRecord = getUser(name);
        UserPlantsTable userPlantsTable = null;
        try {
            userPlantsTable = new UserPlantsTable();
            return userPlantsTable.getPlants(userRecord.getId());
        } finally {
            if (null != userPlantsTable) userPlantsTable.close();
        }
    }
}
