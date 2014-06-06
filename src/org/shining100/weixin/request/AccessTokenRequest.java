package org.shining100.weixin.request;

import java.net.SocketTimeoutException;

import net.sf.json.JSONObject;

/**
 * Created by yangguang on 14-3-19.
 */
public class AccessTokenRequest {
    public static AccessToken request() throws WeiXinRequestException, SocketTimeoutException {
        String url = accessTokenUrl.replace(APPID, appid).replace(APPSECRET, appsecret);

        WeiXinHttpsRequest request = new WeiXinHttpsRequest();
        StringBuffer responseContent = new StringBuffer();
        request.send(url, responseContent);
        JSONObject jsonObject = JSONObject.fromObject(responseContent.toString());
        if (jsonObject.has(errcode))
            throw new WeiXinRequestException(jsonObject.getInt(errcode), jsonObject.getString(errmsg));

        AccessToken accessToken = new AccessToken();
        accessToken.token = jsonObject.getString(access_token);
        accessToken.expires = jsonObject.getInt(expires_in);
        return accessToken;
    }

    private final static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private final static String APPID = "APPID";
    private final static String appid = "wx8d0898c1108cb2a3";
    private final static String APPSECRET = "APPSECRET";
    private final static String appsecret = "068740a6ec4fcb9ec4af437b3c695fa0";
    private final static String errcode = "errcode";
    private final static String errmsg = "errmsg";
    private final static String access_token = "access_token";
    private final static String expires_in = "expires_in";
}
