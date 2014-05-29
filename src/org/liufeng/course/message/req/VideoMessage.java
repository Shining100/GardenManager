package org.liufeng.course.message.req;

/**
 * Created by yangguang on 14-3-7.
 */
public class VideoMessage {
    // 媒体ID
    private String MediaId;

    // 视频消息缩略图的媒体ID
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
