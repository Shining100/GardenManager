package org.shining100.weixin.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

/**
 * Created by yangguang on 2014-03-25.
 */
public class WeiXinHttpRequest {
    public WeiXinHttpRequest() {
        connectTimeout = 5000;
        readTimeout = 15000;
    }

    public WeiXinHttpRequest(int connectTimeout, int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public void send(String urlString, StringBuffer responseContent)
            throws SocketTimeoutException {
        HttpURLConnection connection = null;

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

    public String send(String urlString, String requestContent)
            throws SocketTimeoutException {
        StringBuffer responseContent;
        HttpURLConnection connection = null;

        try {
            connection = createConnection(urlString);
            connection.setDoOutput(true);
            connection.setRequestMethod(POST);
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            // 注意编码格式，防止中文乱码
            outputStream.write(requestContent.getBytes("UTF-8"));
            outputStream.close();

            responseContent = new StringBuffer();
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

        return  responseContent.toString();
    }

    private HttpURLConnection createConnection(String urlString) {
        HttpURLConnection connection;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setUseCaches(false);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    private void readText(HttpURLConnection connection, StringBuffer responseContent) {
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
