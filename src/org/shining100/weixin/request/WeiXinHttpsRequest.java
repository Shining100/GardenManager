package org.shining100.weixin.request;

import java.io.*;

import java.net.URL;
import javax.net.ssl.*;
import java.net.SocketTimeoutException;

import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

/**
 * Created by yangguang on 14-3-20.
 */
public class WeiXinHttpsRequest {
    public WeiXinHttpsRequest() {
        connectTimeout = 5000;
        readTimeout = 15000;
    }

    public WeiXinHttpsRequest(int connectTimeout, int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public void send(String urlString, StringBuffer responseContent)
            throws SocketTimeoutException {
        HttpsURLConnection connection = null;

        try {
            connection = createConnection(urlString);
            connection.connect();
            readText(connection, responseContent);
        }
        catch (SocketTimeoutException e) {
            throw e;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
        if (null != connection)
            connection.disconnect();
        }
    }

    public void send(String urlString, String requestContent, StringBuffer responseContent)
            throws SocketTimeoutException {
        HttpsURLConnection connection = null;

        try {
            connection = createConnection(urlString);
            connection.setDoOutput(true);
            connection.setRequestMethod(POST);
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            // 注意编码格式，防止中文乱码
            outputStream.write(requestContent.getBytes("UTF-8"));
            outputStream.close();

            readText(connection, responseContent);
        }
        catch (SocketTimeoutException e) {
            throw e;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (null != connection)
                connection.disconnect();
        }
    }

    private HttpsURLConnection createConnection(String urlString) {
        HttpsURLConnection connection;
        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setUseCaches(false);
        return connection;
    }

    private void readText(HttpsURLConnection connection, StringBuffer responseContent) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                responseContent.append(s);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (null != bufferedReader)
                    bufferedReader.close();
                if (null != inputStreamReader)
                    inputStreamReader.close();
                if (null != inputStream)
                    inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int connectTimeout;
    private int readTimeout;

    private final static String POST = "POST";
}

class MyX509TrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
