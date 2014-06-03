package org.shining100.project.session;

import java.util.Map;
import java.util.HashMap;

import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.liufeng.course.util.MessageUtil;

import org.shining100.project.db.UserTable;
import org.shining100.project.db.UserRecord;
import org.shining100.project.db.ConnectionPool;

public class InsertPlantSessionTest {
    @Test
    public void test() throws SQLException {
        ConnectionPool.getInstance().init("127.0.0.1", 3306, "root", "123456");
        UserTable table = new UserTable();
        UserRecord record = new UserRecord();
        record.setName("user");
        record.setPassword("password");
        table.delete(record.getName());
        table.insert(record);
        table.close();

        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put(MessageUtil.MESSAGE_HEADER_MSG_TYPE, MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        InsertPlantSession session = new InsertPlantSession();
        String respContent = session.process(requestMap);
        assertThat(respContent, is("亲，请输入你要添加的植物"));
        assertThat(session.state, is(InsertPlantSession.State.PROCESS));
        requestMap.put(MessageUtil.MESSAGE_HEADER_FROM_USER_NAME, "user");
        requestMap.put(MessageUtil.MESSAGE_HEADER_CONTENT, "plant");
        respContent = session.process(requestMap);
        assertThat(respContent, is("亲，您的植物已经添加成功了"));
        assertThat(session.isComplete(), is(true));

        session = new InsertPlantSession();
        respContent = session.process(requestMap);
        assertThat(respContent, is("亲，请输入你要添加的植物"));
        assertThat(session.state, is(InsertPlantSession.State.PROCESS));
        respContent = session.process(requestMap);
        assertThat(respContent, is("亲，您已经添加了该植物了哦"));
        assertThat(session.isComplete(), is(true));
    }
}