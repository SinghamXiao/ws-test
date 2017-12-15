package com.singham.yuan.ws.test.server.remote;

import com.singham.yuan.body.RequestBody;
import com.singham.yuan.body.ResponseBody;
import com.singham.yuan.body.TestBody;
import com.singham.yuan.ws.test.common.factory.ResponseBodyFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ServerEndpoint {

    private static final String NAMESPACE_URI = "http://www.yuan.singham.com/body";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TestBody")
    @ResponsePayload
    public TestBody execute(@RequestPayload TestBody request) {
        return request;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RequestBody")
    @ResponsePayload
    public ResponseBody execute(@RequestPayload RequestBody request) {
        return ResponseBodyFactory.newResponseBody();
    }
}