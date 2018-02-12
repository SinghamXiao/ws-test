package com.singham.yuan.ws.test.server.interceptor;

import com.singham.yuan.ws.test.common.service.TransformMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;

@Component
public class LogEndPointInterceptor implements EndpointInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogEndPointInterceptor.class);

    @Autowired
    @Qualifier("serverTransformMessageService")
    private TransformMessageService transformMessageService;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        String message = transformMessageService.getMessageContent(messageContext.getRequest());
        LOGGER.info("Server-Request: " + message);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        String message = transformMessageService.getMessageContent2(messageContext.getResponse());
        LOGGER.info("Server-Response: " + message);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {

    }
}
