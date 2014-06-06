package org.liufeng.course.message.req;

import org.liufeng.course.message.resp.BaseMessage;

/**
 * Created by yangguang on 2014/6/5.
 */
public class EventMessage extends org.liufeng.course.message.resp.BaseMessage {
    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    String Event;
}
