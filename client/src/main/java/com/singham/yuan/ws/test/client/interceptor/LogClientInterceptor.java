package com.singham.yuan.ws.test.client.interceptor;

import com.singham.yuan.ws.test.common.service.TransformMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

@Component
public class LogClientInterceptor implements ClientInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogClientInterceptor.class);

    @Autowired
    @Qualifier("clientTransformMessageService")
    private TransformMessageService transformMessageService;

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        String message = transformMessageService.getMessageContent(messageContext.getRequest());
        LOGGER.info("Client-Request: " + message);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        String message = transformMessageService.getMessageContent2(messageContext.getResponse());
        LOGGER.info("Client-Response: " + message);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        String message = transformMessageService.getMessageContent(messageContext.getResponse());
        LOGGER.info("Client-Response: " + message);
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }

}
