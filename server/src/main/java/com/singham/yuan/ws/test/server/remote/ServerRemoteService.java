package com.singham.yuan.ws.test.server.remote;

import com.singham.yuan.body.TestBody;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ServerRemoteService {

    private static final String NAMESPACE_URI = "http://www.yuan.singham.com/body";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TestBody")
    @ResponsePayload
    public void execute(@RequestPayload TestBody request) {

        System.out.println("test");
    }
}
