package org.shining100.project.session;

import java.util.LinkedList;

import java.sql.SQLException;

import org.shining100.project.db.UserPlantsRecord;

/**
 * Created by yangguang on 2014/6/3.
 */
public class SessionHelper {
    public static String getUserPlantsText(String name) throws SQLException {
        LinkedList<UserPlantsRecord> records = DbHelper.getUserPlants(name);
        if (records.isEmpty()) return "亲，您还没有添加过植物哦";
        int i = 1;
        String respContent = "";
        for (UserPlantsRecord record : records) {
            respContent += i + "." + record.getName() + "\n";
            ++i;
        }
        return respContent;
    }
}
