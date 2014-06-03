package org.shining100.project.session;

import java.util.Map;

import java.sql.SQLException;

import org.liufeng.course.util.MessageUtil;

import org.shining100.project.db.UserRecord;
import org.shining100.project.db.UserPlantsTable;
import org.shining100.project.db.UserPlantsRecord;

/**
 * Created by yangguang on 2014/5/30.
 */
public class InsertPlantSession implements Session {
    enum State {
        INIT,
        PROCESS,
        COMPLETE
    }

    @Override
    public boolean isComplete() {
        return state.equals(State.COMPLETE);
    }

    @Override
    public String process(Map<String, String> requestMap) {
        String respContent = "亲，服务器处理添加植物出错了，请稍候尝试";
        switch (state) {
            case INIT:
                respContent = "亲，请输入你要添加的植物";
                state = State.PROCESS;
                break;
            case PROCESS:
                String msgType = requestMap.get(MessageUtil.MESSAGE_HEADER_MSG_TYPE);
                if (!msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                    respContent = "亲，您输入的不是植物的名称哦";
                    return respContent;
                }

                UserPlantsTable userPlantsTable = null;
                try {
                    UserRecord userRecord = DbHelper.getUser(requestMap.get(
                            MessageUtil.MESSAGE_HEADER_FROM_USER_NAME));
                    UserPlantsRecord userPlantsRecord = new UserPlantsRecord();
                    userPlantsRecord.setUserId(userRecord.getId());
                    userPlantsRecord.setName(requestMap.get(MessageUtil.MESSAGE_HEADER_CONTENT));
                    userPlantsTable = new UserPlantsTable();
                    userPlantsTable.insert(userPlantsRecord);
                    respContent = "亲，您的植物已经添加成功了";
                }
                catch (SQLException e) {
                    if (e.getSQLState().equals("45000")) respContent = "亲，您已经添加了该植物了哦";
                    else e.printStackTrace();
                } finally {
                    try {
                        if (null != userPlantsTable) userPlantsTable.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                state = State.COMPLETE;
                break;
            default:
                break;
        }
        return respContent;
    }

    State state = State.INIT;
}
