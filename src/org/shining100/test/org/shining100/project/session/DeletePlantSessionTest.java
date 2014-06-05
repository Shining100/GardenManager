package org.shining100.project.session;

import java.util.Map;
import java.util.HashMap;

import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.liufeng.course.util.MessageUtil;

import org.shining100.project.db.*;

public class DeletePlantSessionTest {
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
        DeletePlantSession session = new DeletePlantSession();
        String respContent = session.process(requestMap);
        assertThat(session.isComplete(), is(true));
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

        session = new DeletePlantSession();
        respContent = session.process(requestMap);
        assertThat(session.state, is(DeletePlantSession.State.PROCESS));
        assertThat(respContent, is("亲，您添加了如下所示植物\n" +
                "\n1.plant3\n2.plant2\n3.plant\n\n" +
                "请输入你所要删除植物的序号"));

        requestMap.put(MessageUtil.MESSAGE_HEADER_MSG_TYPE, MessageUtil.REQ_MESSAGE_TYPE_EVENT);
        respContent = session.process(requestMap);
        assertThat(session.state, is(DeletePlantSession.State.PROCESS));
        assertThat(respContent, is("亲，您输入的不是要删除植物的序号哦"));

        requestMap.put(MessageUtil.MESSAGE_HEADER_MSG_TYPE, MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        requestMap.put(MessageUtil.MESSAGE_HEADER_CONTENT, "aaa");
        respContent = session.process(requestMap);
        assertThat(session.state, is(DeletePlantSession.State.PROCESS));
        assertThat(respContent, is("亲，您输入的不是序号哦"));

        requestMap.put(MessageUtil.MESSAGE_HEADER_CONTENT, "9");
        respContent = session.process(requestMap);
        assertThat(session.state, is(DeletePlantSession.State.PROCESS));
        assertThat(respContent, is("亲，您输入的序号超过选取范围了哦"));

        requestMap.put(MessageUtil.MESSAGE_HEADER_CONTENT, "2");
        respContent = session.process(requestMap);
        assertThat(session.isComplete(), is(true));
        assertThat(respContent, is("亲，你所指定的植物已经成功删除了"));

        session = new DeletePlantSession();
        respContent = session.process(requestMap);
        assertThat(session.state, is(DeletePlantSession.State.PROCESS));
        assertThat(respContent, is("亲，您添加了如下所示植物\n" +
                "\n1.plant3\n2.plant\n\n" +
                "请输入你所要删除植物的序号"));
    }
}
