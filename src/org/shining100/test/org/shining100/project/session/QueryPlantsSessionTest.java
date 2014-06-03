package org.shining100.project.session;

import java.util.Map;
import java.util.HashMap;

import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.liufeng.course.util.MessageUtil;

import org.shining100.project.db.*;

public class QueryPlantsSessionTest {
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

        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put(MessageUtil.MESSAGE_HEADER_FROM_USER_NAME, "user");
        QueryPlantsSession session = new QueryPlantsSession();
        String respContent = session.process(requestMap);
        assertThat(respContent, is("亲，您还没有添加过植物哦"));

        UserPlantsRecord userPlantsRecord = new UserPlantsRecord();
        userPlantsRecord.setUserId(userRecord.getId());
        UserPlantsTable userPlantsTable = new UserPlantsTable();
        String[] plants = new String[]{"plant", "plant2", "plant3"};
        for (String plant : plants) {
            userPlantsRecord.setName(plant);
            userPlantsTable.insert(userPlantsRecord);
        }
        userPlantsTable.close();
        userTable.close();

        respContent = session.process(requestMap);
        assertThat(respContent, is("1.plant3\n2.plant2\n3.plant\n"));
    }
}
