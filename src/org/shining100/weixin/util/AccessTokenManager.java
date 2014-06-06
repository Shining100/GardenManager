package org.shining100.weixin.util;

import java.net.SocketTimeoutException;

import org.shining100.weixin.request.AccessToken;
import org.shining100.weixin.request.AccessTokenRequest;
import org.shining100.weixin.request.WeiXinRequestException;

/**
 * Created by yangguang on 14-3-19.
 */
public class AccessTokenManager {
    public static String getAccessToken() {
        return accessToken.token;
    }

    public static boolean isAccessTokenErrcode(int errorCode) {
        return 40001 == errorCode || 40014 == errorCode || 41001 == errorCode || 42001 == errorCode;
    }

    public static synchronized void update() throws WeiXinRequestException, SocketTimeoutException {
        if (7000 <= accessToken.expires) {
            System.out.println("AccessToken expires = " + accessToken.expires + ", ignore update");
            return;
        }

        accessToken = AccessTokenRequest.request();
    }

    private static AccessToken accessToken = new AccessToken();

    public static void main(String args[]) {
        try {
            update();
            System.out.println("access_token = " +  getAccessToken());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
