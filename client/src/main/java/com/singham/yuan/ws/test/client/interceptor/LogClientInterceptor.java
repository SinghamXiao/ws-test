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

import java.util.concurrent.ThreadPoolExecutor;

@Component
public class LogClientInterceptor implements ClientInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogClientInterceptor.class);

    private static final Logger HTTP_LOGGER = LoggerFactory.getLogger("HTTP_LOGGER");

    @Autowired
    @Qualifier("clientTransformMessageService")
    private TransformMessageService transformMessageService;

    @Autowired
    @Qualifier("streamLogRecorderExecutor")
    private ThreadPoolExecutor streamLogRecorderExecutor;

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        String message = transformMessageService.getMessageContent(messageContext.getRequest());
        String info = "Client-Request: " + message;
        LOGGER.info(info);
        streamLogRecorderExecutor.execute(() -> HTTP_LOGGER.info(info));
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        String message = transformMessageService.getMessageContent2(messageContext.getResponse());
        String info = "Client-Response: " + message;
        LOGGER.info(info);
        streamLogRecorderExecutor.execute(() -> HTTP_LOGGER.info(info));
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        String message = transformMessageService.getMessageContent(messageContext.getResponse());
        String info = "Client-Response: " + message;
        LOGGER.info(info);
        streamLogRecorderExecutor.execute(() -> HTTP_LOGGER.info(info));
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }

}
