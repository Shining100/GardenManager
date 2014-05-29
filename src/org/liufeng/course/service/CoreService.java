package org.liufeng.course.service;

import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.liufeng.course.util.MessageUtil;
import org.liufeng.course.message.resp.TextMessage;

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
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {

            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {

            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {

            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {

            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {

            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            respContent = "亲，服务器处理出错了，请稍候尝试";
        }

        textMessage.setContent(respContent);
        return MessageUtil.textMessageToXml(textMessage);
    }
}
