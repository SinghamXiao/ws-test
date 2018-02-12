package com.singham.yuan.ws.test.client.http;

import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import java.io.IOException;
import java.net.HttpURLConnection;

public class EnhanceHttpUrlConnectionMessageSender extends HttpUrlConnectionMessageSender {

    private int readTimeout = 10000;

    private int connectionTimeout = 10000;

    @Override
    protected void prepareConnection(HttpURLConnection connection) throws IOException {
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(connectionTimeout);
        super.prepareConnection(connection);
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
