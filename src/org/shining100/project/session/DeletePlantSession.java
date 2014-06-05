package org.shining100.project.session;

import java.util.Map;
import java.util.LinkedList;

import java.sql.SQLException;

import org.liufeng.course.util.MessageUtil;

import org.shining100.project.db.UserPlantsTable;
import org.shining100.project.db.UserPlantsRecord;

/**
 * Created by yangguang on 2014/6/4.
 */
public class DeletePlantSession implements Session {
    enum State {
        INIT,
        PROCESS,
        COMPLETE
    }

    @Override
    public String process(Map<String, String> requestMap) {
        String respContent = "亲，服务器处理删除植物出错了，请稍候尝试";
        try {
            switch (state) {
                case INIT:
                    records = DbHelper.getUserPlants(requestMap.get(MessageUtil.MESSAGE_HEADER_FROM_USER_NAME));
                    if (records.isEmpty()) {
                        state = State.COMPLETE;
                        return "亲，您还没有添加过植物哦";
                    }
                    int i = 1;
                    respContent = "亲，您添加了如下所示植物\n\n";
                    for (UserPlantsRecord record : records) {
                        respContent += i + "." + record.getName() + "\n";
                        ++i;
                    }
                    respContent += "\n请输入你所要删除植物的序号";

                    state = State.PROCESS;
                    break;
                case PROCESS:
                    String msgType = requestMap.get(MessageUtil.MESSAGE_HEADER_MSG_TYPE);
                    if (!msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) return "亲，您输入的不是要删除植物的序号哦";
                    int index = Integer.parseInt(requestMap.get(MessageUtil.MESSAGE_HEADER_CONTENT));
                    if (1 > index || records.size() < index) return "亲，您输入的序号超过选取范围了哦";
                    UserPlantsRecord record = records.get(index - 1);
                    UserPlantsTable table = new UserPlantsTable();
                    table.delete(record.getId());

                    respContent = "亲，你所指定的植物已经成功删除了";
                    state = State.COMPLETE;
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            return "亲，您输入的不是序号哦";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return respContent;
    }

    @Override
    public boolean isComplete() {
        return state.equals(State.COMPLETE);
    }

    State state = State.INIT;
    LinkedList<UserPlantsRecord> records;
}
