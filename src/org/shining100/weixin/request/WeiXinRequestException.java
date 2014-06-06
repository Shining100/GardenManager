package org.shining100.weixin.request;

/**
 * 微信请求异常,可以通过该异常得到微信服务器返回的错误码和描述.
 */
public class WeiXinRequestException extends Exception{
    public WeiXinRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private int code;
}
