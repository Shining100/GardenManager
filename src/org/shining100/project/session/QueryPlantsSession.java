package org.shining100.project.session;

import java.util.Map;

import java.sql.SQLException;

import org.liufeng.course.util.MessageUtil;

/**
 * Created by yangguang on 2014/6/3.
 */
public class QueryPlantsSession implements Session {
    @Override
    public String process(Map<String, String> requestMap) {
        String respContent = "亲，服务器处理查询植物出错了，请稍候尝试";

        try {
            respContent = SessionHelper.getUserPlantsText(requestMap.get(MessageUtil.MESSAGE_HEADER_FROM_USER_NAME));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return respContent;
    }

    @Override
    public boolean isComplete() {
        return true;
    }
}
