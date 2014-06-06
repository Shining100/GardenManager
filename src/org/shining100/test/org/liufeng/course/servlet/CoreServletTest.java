package org.liufeng.course.servlet;

import java.util.Date;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

import org.liufeng.course.util.MessageUtil;
import org.liufeng.course.message.req.MenuMessage;
import org.liufeng.course.message.req.TextMessage;
import org.liufeng.course.message.req.EventMessage;
import org.liufeng.course.message.req.LocationMessage;

import org.liufeng.course.service.CoreService;

import org.shining100.weixin.request.WeiXinHttpRequest;

import org.shining100.project.db.UserTable;

public class CoreServletTest {
    @Test
    public void test() throws Exception {
        Server server = new Server(80);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");
        servletContextHandler.setResourceBase(".");
        ServletHolder servletHolder = new ServletHolder(new CoreServlet());
        servletContextHandler.addServlet(servletHolder, "/GardenManager");
        server.setHandler(servletContextHandler.getServletHandler());
        server.start();

        UserTable table = new UserTable();
        table.delete();
        table.close();

        String users[] = new String[]{"user", "user2", "user3"};
        WeiXinHttpRequest request = new WeiXinHttpRequest();
        for (String user : users) {
            EventMessage eventMessage = createEventMessage(user, "subscribe");
            String requestContent = MessageUtil.evnetMessageToXml(eventMessage);
            String responseContent = request.send(servletUrl, requestContent);
            assertThat(responseContent, containsString("亲，欢迎您关注花草管家"));
        }

        String plants[] = new String[]{"plant", "plant2", "plant3"};
        for (String plant : plants) {
            for (String user : users) {
                MenuMessage menuMessage = createMenuMessage(user, "1");
                String requestContent = MessageUtil.menuMessageToXml(menuMessage);
                String responseContent = request.send(servletUrl, requestContent);
                assertThat(responseContent, containsString("亲，请输入你要添加的植物"));
            }
            assertThat(CoreService.sessions.size(), is(3));
            for (String user : users){
                TextMessage textMessage = createTextMessage(user, plant);
                String requestContent = MessageUtil.textMessageToXml(textMessage);
                String responseContent = request.send(servletUrl, requestContent);
                assertThat(responseContent, containsString("亲，您的植物已经添加成功了"));
            }
            assertThat(CoreService.sessions.isEmpty(), is(true));
        }

        for (String user : users) {
            MenuMessage menuMessage = createMenuMessage(user, "12");
            String requestContent = MessageUtil.menuMessageToXml(menuMessage);
            String responseContent = request.send(servletUrl, requestContent);
            assertThat(responseContent, containsString("1.plant32.plant23.plant"));  // WeiXinHttpRequest读取方式不对，导致\n消失?
        }
        assertThat(CoreService.sessions.isEmpty(), is(true));

        for (String user : users) {
            MenuMessage menuMessage = createMenuMessage(user, "13");
            String requestContent = MessageUtil.menuMessageToXml(menuMessage);
            String responseContent = request.send(servletUrl, requestContent);
            assertThat(responseContent, containsString("1.plant32.plant23.plant"));
        }
        assertThat(CoreService.sessions.size(), is(3));
        for (String user : users){
            TextMessage textMessage = createTextMessage(user, "2");
            String requestContent = MessageUtil.textMessageToXml(textMessage);
            String responseContent = request.send(servletUrl, requestContent);
            assertThat(responseContent, containsString("亲，你所指定的植物已经成功删除了"));
        }
        assertThat(CoreService.sessions.isEmpty(), is(true));
        for (String user : users) {
            MenuMessage menuMessage = createMenuMessage(user, "12");
            String requestContent = MessageUtil.menuMessageToXml(menuMessage);
            String responseContent = request.send(servletUrl, requestContent);
            assertThat(responseContent, containsString("1.plant32.plant"));
        }
        assertThat(CoreService.sessions.isEmpty(), is(true));

        for (String user : users) {
            EventMessage eventMessage = createEventMessage(user, "unsubscribe");
            String requestContent = MessageUtil.evnetMessageToXml(eventMessage);
            request.send(servletUrl, requestContent);
        }

        server.stop();
    }

    EventMessage createEventMessage(String eventType) {
        return createEventMessage(fromUser, eventType);
    }

    EventMessage createEventMessage(String fromUser, String eventType) {
        EventMessage eventMessage = new EventMessage();
        initMessage(eventMessage, fromUser, event);
        eventMessage.setEvent(eventType);
        return eventMessage;
    }

    MenuMessage createMenuMessage(String eventKey) {
        return createMenuMessage(fromUser, eventKey);
    }

    MenuMessage createMenuMessage(String fromUser, String eventKey) {
        MenuMessage menuMessage = new MenuMessage();
        initMessage(menuMessage, fromUser, event);
        menuMessage.setEvent(CLICK);
        menuMessage.setEventKey(eventKey);
        return menuMessage;
    }

    LocationMessage createLocationMessage() {
        LocationMessage locationMessage = new LocationMessage();
        initMessage(locationMessage, location);
        locationMessage.setLocation_X(locationX);
        locationMessage.setLocation_Y(locationY);
        locationMessage.setScale(scale);
        locationMessage.setLabel(Label);
        return locationMessage;
    }

    TextMessage createTextMessage(String content) {
        return createTextMessage(fromUser, content);
    }

    TextMessage createTextMessage(String fromUser, String content) {
        TextMessage textMessage = new TextMessage();
        initMessage(textMessage, fromUser, text);
        textMessage.setContent(content);
        return textMessage;
    }

    void initMessage(org.liufeng.course.message.req.BaseMessage baseMessage, String msgType) {
       initMessage(baseMessage, fromUser, msgType);
    }

    void initMessage(org.liufeng.course.message.req.BaseMessage baseMessage, String fromUser, String msgType) {
        baseMessage.setToUserName(toUser);
        baseMessage.setFromUserName(fromUser);
        baseMessage.setCreateTime(new Date().getTime());
        baseMessage.setMsgType(msgType);
        baseMessage.setMsgId(msgId++);
    }

    void initMessage(org.liufeng.course.message.resp.BaseMessage baseMessage, String msgType) {
        initMessage(baseMessage, fromUser, msgType);
    }

    void initMessage(org.liufeng.course.message.resp.BaseMessage baseMessage, String fromUser, String msgType) {
        baseMessage.setToUserName(toUser);
        baseMessage.setFromUserName(fromUser);
        baseMessage.setCreateTime(new Date().getTime());
        baseMessage.setMsgType(msgType);
    }

    static final String servletUrl = "http://127.0.0.1/GardenManager";

    static final String toUser = "toUser";
    static final String fromUser = "fromUser";
    static final String event = "event";
    static final String CLICK = "CLICK";
    static final String location = "location";
    static final String locationX = "23.134521";
    static final String locationY = "113.358803";
    static final String scale = "20";
    static final String Label = "Label";
    static final String text = "text";

    private long msgId = 1234567890123456L;
}
