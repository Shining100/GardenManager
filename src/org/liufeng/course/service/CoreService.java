package org.liufeng.course.service;

import java.util.Map;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.liufeng.course.util.MessageUtil;
import org.liufeng.course.message.resp.TextMessage;

import org.shining100.project.db.UserTable;
import org.shining100.project.db.UserRecord;

import org.shining100.project.session.Session;
import org.shining100.project.session.InsertPlantSession;
import org.shining100.project.session.QueryPlantsSession;
import org.shining100.project.session.DeletePlantSession;

/**
 * 核心服务类
 *
 * @author liufeng
 * @date 2013-05-20
 */
public class CoreService {
    public static String processRequest(HttpServletRequest request, HttpServletResponse response) {
        TextMessage textMessage = new TextMessage();
        // 默认返回的文本消息内容
        String respContent = "";

        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get(MessageUtil.MESSAGE_HEADER_FROM_USER_NAME);
            // 公众帐号
            String toUserName = requestMap.get(MessageUtil.MESSAGE_HEADER_TO_USER_NAME);
            // 消息类型
            String msgType = requestMap.get(MessageUtil.MESSAGE_HEADER_MSG_TYPE);

            // 回复文本消息
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                Session session = sessions.get(fromUserName);
                do {
                    if (null == session) {
                        respContent = "亲，请先用菜单选择您的操作";
                        break;
                    }
                    respContent = session.process(requestMap);
                    if (session.isComplete()) sessions.remove(fromUserName);
                } while (false);
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "亲，我们暂时不支持地理信息消息哦";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "亲，我们暂时不支持图片消息哦";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "亲，我们暂时不支持视频消息哦";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "亲，我们暂时不支持音频消息哦";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "亲，我们暂时不支持链接消息哦";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get(MessageUtil.MESSAGE_HEADER_EVENT);
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = processSubscribe(fromUserName);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                    processUnSubscribe(fromUserName);
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    if (sessions.containsKey(fromUserName))  sessions.remove(fromUserName);
                    String eventKey = requestMap.get("EventKey");
                    Session session;
                    do {
                        if (eventKey.equals("1")) session = new InsertPlantSession();
                        else if (eventKey.equals("12")) session = new QueryPlantsSession();
                        else if (eventKey.equals("13")) session = new DeletePlantSession();
                        else {
                            respContent = "亲，腾讯发过来了未知的菜单项，请稍后再试";
                            break;
                        }
                        if (null != sessions.putIfAbsent(fromUserName, session)) {
                            respContent = "";
                            break;
                        }
                        respContent = session.process(requestMap);
                        if (session.isComplete()) sessions.remove(fromUserName);
                    } while (false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            respContent = "亲，服务器处理出错了，请稍候尝试";
        }

        textMessage.setContent(respContent);
        return MessageUtil.textMessageToXml(textMessage);
    }

    static String processSubscribe(String fromUserName) throws SQLException {
        UserRecord record = new UserRecord();
        record.setName(fromUserName);
        record.setPassword(fromUserName);
        UserTable table = new UserTable();
        table.insert(record);
        return "亲，欢迎您关注花草管家";
    }

    static void processUnSubscribe(String fromUserName) throws SQLException {
        UserTable table = new UserTable();
        table.delete(fromUserName);
    }

    static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>(4096);
}
