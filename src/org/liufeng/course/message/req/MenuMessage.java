package org.liufeng.course.message.req;

import org.liufeng.course.message.resp.*;

/**
 * Created by yangguang on 2014-03-25.
 */
public class MenuMessage extends org.liufeng.course.message.resp.BaseMessage {
    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    private String Event;
    private String EventKey;
}
