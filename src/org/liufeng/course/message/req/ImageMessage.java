package org.liufeng.course.message.req;

/**
 * 图片消息
 *
 * @author liufeng
 * @date 2013-05-19
 */
public class ImageMessage extends BaseMessage {
    // 图片链接
    private String PicUrl;

    // 媒体ID
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
