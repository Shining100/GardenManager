package org.shining100.weixin.request;

import java.net.SocketTimeoutException;

import net.sf.json.JSONObject;

import org.shining100.weixin.util.AccessTokenManager;

/**
 * 微信客服消息.协议见<a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF"/>.
 */
public class TextMessageRequest {
    /**
     * 发送微信客服消息.
     * @param user 用户名.即微信用户的OpenID.
     * @param content 发送给用户的消息,为了能保证在微信上正常显示,请使用utf-8编码.
     * @throws WeiXinRequestException 微信服务器返回非0错误码时产生.
     * @throws SocketTimeoutException 连接微信服务器失败时产生.
     * @throws Exception 其它异常.
     */
    public static void request(String user, String content) throws WeiXinRequestException,
            SocketTimeoutException {
        String url = textMessageUrl.replace(ACCESS_TOKEN, AccessTokenManager.getAccessToken());

        JSONObject requestContent = new JSONObject();
        requestContent.put(touser, user);
        requestContent.put(msgtype, text);
        JSONObject contentObject = new JSONObject();
        contentObject.put(TextMessageRequest.content, content);
        requestContent.put(text, contentObject);

        WeiXinHttpsRequest request = new WeiXinHttpsRequest();
        StringBuffer responseContent = new StringBuffer();
        request.send(url, requestContent.toString(), responseContent);
        JSONObject jsonObject = JSONObject.fromObject(responseContent.toString());
        int errcode = jsonObject.getInt(TextMessageRequest.errcode);
        if (0 == errcode)
            return;
        else if (!AccessTokenManager.isAccessTokenErrcode(errcode))
            throw new WeiXinRequestException(errcode, jsonObject.getString(errmsg));

        AccessTokenManager.update();
        url = textMessageUrl.replace(ACCESS_TOKEN, AccessTokenManager.getAccessToken());
        responseContent = new StringBuffer();
        request.send(url, requestContent.toString(), responseContent);
        jsonObject = JSONObject.fromObject(responseContent.toString());
        errcode = jsonObject.getInt(TextMessageRequest.errcode);
        if (0 != errcode)
            throw new WeiXinRequestException(errcode, jsonObject.getString(errmsg));
    }

    private static final String textMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private final static String touser = "touser";
    private final static String msgtype = "msgtype";
    private final static String text = "text";
    private final static String content = "content";
    private final static String errcode = "errcode";
    private final static String errmsg = "errmsg";

    public static void main(String args[]) {
        try {
            TextMessageRequest.request("123", "123");
        }
        catch (WeiXinRequestException e) {
            System.err.println("TextMessageRequest failed: errcode = " + e.getCode() + ", errmsg = " + e.getMessage());
        }
        catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
    }
}
