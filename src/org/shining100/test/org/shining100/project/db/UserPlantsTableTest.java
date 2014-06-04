package org.shining100.project.db;

import java.util.LinkedList;

import java.sql.SQLException;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.is;

public class UserPlantsTableTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test() throws SQLException {
        ConnectionPool.getInstance().init("127.0.0.1", 3306, "root", "123456");
        UserTable userTable = new UserTable();
        UserRecord userRecord = new UserRecord();
        userRecord.setName("user");
        userRecord.setPassword("password");
        userTable.delete(userRecord.getName());
        userTable.insert(userRecord);
        userRecord = userTable.getUser(userRecord.getName());
        UserPlantsTable userPlantsTable = new UserPlantsTable();
        UserPlantsRecord userPlantsRecord = new UserPlantsRecord();
        userPlantsRecord.setUserId(userRecord.getId());
        userPlantsRecord.setName("青叶绿萝");
        userPlantsTable.insert(userPlantsRecord);
        exception.expect(SQLException.class);
        exception.expectMessage("用户已添加该植物");
        userPlantsTable.insert(userPlantsRecord);
        LinkedList<UserPlantsRecord> userPlantsRecords = userPlantsTable.getPlants(
                userPlantsRecord.getUserId());
        assertThat(userPlantsRecords.size(), is(1));
        assertThat(userPlantsRecords.getFirst().getUserId(), is(userPlantsRecord.getUserId()));
        assertThat(userPlantsRecords.getFirst().getName(), is(userPlantsRecord.getName()));
        userPlantsTable.delete(userPlantsRecord.getId());
        userPlantsRecords = userPlantsTable.getPlants(userPlantsRecord.getUserId());
        assertThat(userPlantsRecords.isEmpty(), is(true));
        userPlantsTable.close();
        userTable.close();
    }
}